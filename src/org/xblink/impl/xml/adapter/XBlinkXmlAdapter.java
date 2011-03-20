package org.xblink.impl.xml.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xblink.api.Adapter;
import org.xblink.core.Element;

/**
 * XBlink专用XML解析器，如果用来解析非XBlink序列化的文件，本解析器将无法保证解析结果的正确性 <br>
 * 本解析器在解析过程中区分文件中的特殊标志位，所以不产生回溯
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * 
 */
public class XBlinkXmlAdapter implements Adapter {
private int count=0;
	// 特殊字符标记状态
	private State tagStartState;
	private State tagEndState;
	private State blankState;
	private State equleState;
	private State slashState;
	private State exclamationState;
	private State questionState;
	// 状态机
	private StateMachine stateMachine = new StateMachine(2);
	// 是否字面值
	private boolean isLiteral = false;
	// 是否忽略
	// 空格是否特殊字符
	private boolean isSpecial = false;
	private boolean isIgnore = false;
	int size = 0;// 文件指针，用来标识当前读取位置
	private ReadableByteChannel channelIn = null;// 读取channel
	private WritableByteChannel channelOut = null;// 写入channel
	int buffSize = 40960;
	byte[] bytes = new byte[buffSize];
	ByteBuffer byteBuffer = ByteBuffer.allocate(buffSize);
	private XBlinkQueue queue = new XBlinkQueue();// 解析流的缓存
	private XBlinkQueue temp = new XBlinkQueue();

	public XBlinkXmlAdapter() {
		tagStartState = new State('<');
		tagEndState = new State('>');
		blankState = new State(' ');
		equleState = new State('=');
		slashState = new State('/');
		exclamationState = new State('!');
		questionState = new State('?');
	}


	@Override
	public Element next() {
		Element element = null;
		String attributeName = null;
		String attributeValue = null;
		byte character = 0;
		while (true) {
			while (queue.empty() == false) {
				character = queue.pop();
				//System.out.print((char) character);
				if (character == '"') {
					isLiteral = !isLiteral;
				}
				// 直接添加字面值
				if (isLiteral) {
					temp.push(character);
					continue;
				}
				if (isIgnore) {// 解除忽略状态的条件
					switch (character) {
					case '?':
						stateMachine.addState(questionState);
						break;
					case '>':
						stateMachine.addState(tagEndState);
						break;
					}
					if (stateMachine.size() == 2) {

						if (stateMachine.getState(0).getMark() == '?'
								&& stateMachine.getState(1).getMark() == '>') {
							stateMachine.reset();
							isIgnore = false;
							temp.clear();
						}
					}
					continue;
				}

				// 过滤空白字符
				if (character == '\r' || character == '\n' || character == '\t') {
					continue;
				}
				switch (character) {
				case '<':
					isSpecial = true;
					stateMachine.addState(tagStartState);
					break;
				case ' ':
					if (isSpecial) {
						stateMachine.addState(blankState);
					}
					break;
				case '=':
					stateMachine.addState(equleState);
					break;
				case '/':
					stateMachine.addState(slashState);
					break;
				case '>':
					isSpecial = false;
					stateMachine.addState(tagEndState);
					break;
				case '!':
					stateMachine.addState(exclamationState);
					break;
				case '?':
					stateMachine.addState(questionState);
					break;
				default:
					temp.push(character);
				}
				if (stateMachine.size() == 2) {
					// <?
					if (stateMachine.getState(0).getMark() == '<'
							&& stateMachine.getState(1).getMark() == '?') {
						stateMachine.reset();
						isIgnore = true;
						continue;
					}
					// ?>
					if (stateMachine.getState(0).getMark() == '?'
							&& stateMachine.getState(1).getMark() == '>') {
						stateMachine.reset();
						isIgnore = false;
						temp.clear();
						continue;
					}
					// <_ <> 取tagName
					if ((stateMachine.getState(0).getMark() == '<' && stateMachine
							.getState(1).getMark() == ' ')
							|| (stateMachine.getState(0).getMark() == '<' && stateMachine
									.getState(1).getMark() == '>')) {
						State state = stateMachine.getState(1);
						stateMachine.reset();
						stateMachine.addState(state);
						if (element != null) {
							return element;
						}
						element = new Element();
						element.setName(temp.toString());
						continue;
					}
					// _= 取attribute name
					if (stateMachine.getState(0).getMark() == ' '
							&& stateMachine.getState(1).getMark() == '=') {
						State state = stateMachine.getState(1);
						stateMachine.reset();
						stateMachine.addState(state);
						attributeName = temp.toString();
						continue;
					}
					// =_ => =/取attributeValue
					if ((stateMachine.getState(0).getMark() == '=' && stateMachine
							.getState(1).getMark() == ' ')
							|| (stateMachine.getState(0).getMark() == '=' && stateMachine
									.getState(1).getMark() == '>')
							|| (stateMachine.getState(0).getMark() == '=' && stateMachine
									.getState(1).getMark() == '/')) {
						State state = stateMachine.getState(1);
						stateMachine.reset();
						stateMachine.addState(state);
						attributeValue = temp.toString();
						if (element.getAttributes() == null) {
							element
									.setAttributes(new HashMap<String, String>());
						}
						element.getAttributes().put(attributeName,
								attributeValue);
						continue;
					}
					// >< 取value
					if (stateMachine.getState(0).getMark() == '>'
							&& stateMachine.getState(1).getMark() == '<') {
						State state = stateMachine.getState(1);
						stateMachine.reset();
						stateMachine.addState(state);
						if (element != null) {
							element.setValue(temp.toString());
							return element;
						}
						temp.clear();
						continue;
					}
					// </ />tag结束
					if ((stateMachine.getState(0).getMark() == '<' && stateMachine
							.getState(1).getMark() == '/')) {
						State state = stateMachine.getState(1);
						stateMachine.reset();
						stateMachine.addState(state);
						continue;
					}
					if (stateMachine.getState(0).getMark() == '/'
							&& stateMachine.getState(1).getMark() == '>') {
						State state = stateMachine.getState(1);
						stateMachine.reset();
						stateMachine.addState(state);
						temp.clear();
						isIgnore = false;
						if(element!=null){
							return element;
						}
					}
				}
			}
			if (hasNext()) {
				read();
			} else {
				break;
			}
		}
//		try {
//			System.out.println("---------------------------------------------");	
//			channelIn.close();
//			// TODO Auto-generated catch block
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		return element;
	}
	/**
	 * 流中是否还有未读取的部分
	 * 
	 * @return
	 */
	private boolean hasNext() {
		try {
			if (channelIn.read(byteBuffer) != -1) {
				return true;
			}
			//channelIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取流中的下一部分
	 * 
	 * @return
	 */
	private XBlinkQueue read() {
		size = byteBuffer.position();
		byteBuffer.rewind();
		byteBuffer.get(bytes);
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 0) {
				break;
			}
			//System.out.print((char)bytes[i]);
			queue.push(bytes[i]);
		}
		byteBuffer.clear();
		return queue;
	}
	@Override
	public void setInputStream(InputStream in) {
		// TODO Auto-generated method stub
		channelIn = Channels.newChannel(in);		
		read();
		// for (int i = 0; i < 38; i++) {
		// queue.pop();
		// }// 去除<?xml version="1.0" encoding="UTF-8"?>信息
	}

	// /////////////////////////////写操作相关////////////////////////////////////

	@Override
	public void setOutStream(OutputStream out) {
		channelOut = Channels.newChannel(out);
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
			byteBuffer.put("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					.getBytes("utf-8"));
			channelOut.write(byteBuffer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String put(Element element) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append("<");
		buffer.append(element.getName());
		if (element.getAttributes().size() > 0) {
			for (Object key : element.getAttributes().entrySet().toArray()) {
				buffer.append(" ");
				buffer.append(key);
				buffer.append("=");
				buffer.append(element.getAttributes().get(key));
			}
		}
		buffer.append(">");
		buffer.append(element.getValue());
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
			byteBuffer.put(buffer.toString().getBytes("utf-8"));
			channelOut.write(byteBuffer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public String putDone(Element element) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("</");
		buffer.append(element.getName());
		buffer.append(">");
		try {
			byteBuffer.put(buffer.toString().getBytes("utf-8"));
			channelOut.write(byteBuffer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

	// main方法，用来做简单的测试
	public static void main(String[] args) throws IOException,
			DocumentException {
		for (int i = 0; i < 1; i++) {
			System.out.println("反序列化BigXML_XBlink.xml，文件大小为79.1MB");
			XBlinkXmlAdapter xBlinkXmlAdapter = new XBlinkXmlAdapter();
			xBlinkXmlAdapter.setInputStream(new FileInputStream(new File(
					"d:\\BigXML_XBlink3.xml")));
			long a = System.nanoTime();
			while (true) {
				
				Element element = xBlinkXmlAdapter.next();
				if (element != null) {
					//System.out.println(element.getName());
					//System.out.println(element.getAttributes());
					//System.out.println(element.getValue());
				} else {
					break;
				}
			}
			System.out.println("XBlinkXmlAdapter:");
			System.out.println((System.nanoTime() - a) + "ns");
			a = System.nanoTime();
			Document document = new SAXReader().read(new File(
					"d:\\BigXML_XBlink3.xml"));
			System.out.println("Dom4j:");
			System.out.println((System.nanoTime() - a) + "ns");
		}
		// Element element = new Element();
		// Element element2 = new Element();
		// element.setName("objectA");
		// element2.setName("objectB");
		// XBlinkXmlAdapter xBlinkXmlAdapter = new XBlinkXmlAdapter();
		// xBlinkXmlAdapter.setOutStream(new FileOutputStream(new File(
		// "c:\\aa.xml")));
		// long a = System.nanoTime();
		// for (int i = 0; i < 1000; i++) {
		// xBlinkXmlAdapter.put(element);
		// xBlinkXmlAdapter.put(element2);
		// xBlinkXmlAdapter.putDone(element);
		// xBlinkXmlAdapter.putDone(element);
		// }
		// System.out.println(System.nanoTime() - a);
	}


	@Override
	public int getHierarchy() {
		// TODO Auto-generated method stub
		return 0;
	}
}