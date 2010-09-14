package org.xblink.impl.xml.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xblink.api.Adapter;
import org.xblink.api.AdapterFactory;
import org.xblink.core.Element;

public class XBlinkXmlAdapter_1 implements Adapter {
	int size = 0;// 文件指针，用来标识当前读取位置
	private ReadableByteChannel channelIn = null;// 读取channel
	private WritableByteChannel channelOut = null;// 写入channel
	int buffSize = 1024;
	byte[] bytes = new byte[buffSize];
	ByteBuffer byteBuffer = ByteBuffer.allocate(buffSize);
	private StringBuffer buffer = new StringBuffer(1024);// 解析流的缓存

	@Override
	public Element find(String prefix) {
		return null;
	}

	@Override
	public Element next() {
		Element element = null;
		if (buffer.length() == 0) {
			return element;
		}
		ignoreTag(buffer);
		element = new Element();
		element.setName(getTagName(buffer));
		element.setAttributes(getAttributes(buffer));
		element.setValue(getValue(buffer));
		return element;
	}

	@Override
	public void setInputStream(InputStream in) {
		// TODO Auto-generated method stub
		channelIn = Channels.newChannel(in);
		if (hasNext()) {
			buffer.append(read());
		}
		buffer.delete(0, 38);// 去除<?xml version="1.0" encoding="UTF-8"?>信息
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
			channelIn.close();
		}
		catch (IOException e) {
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
	private String read() {
		String tmp = null;
		size = byteBuffer.position();
		byteBuffer.rewind();
		byteBuffer.get(bytes);
		tmp = new String(bytes, 0, size);
		byteBuffer.clear();
		return tmp;
	}

	// //////////////////////////读操作相关//////////////////////////////////////////////
	/**
	 * 获得流中的第一个tagName
	 * 
	 * @param stringBuffer
	 * @return
	 */
	private String getTagName(StringBuffer stringBuffer) {
		int end = 0;
		boolean hasBegin = false;
		boolean flag = false;
		StringBuffer temp = new StringBuffer();
		int i = 0;
		while (flag == false) {
			for (; i < stringBuffer.length(); i++) {
				if (stringBuffer.charAt(i) == '<') {
					hasBegin = true;
				}
				if (stringBuffer.charAt(i) == '\r'
					|| stringBuffer.charAt(i) == '\n'
					|| stringBuffer.charAt(i) == '\t') {
					continue;
				}
				if (stringBuffer.charAt(i) == ' '
					|| stringBuffer.charAt(i) == '/'
					|| stringBuffer.charAt(i) == '>') {
					if (hasBegin) {
						end = i;
						flag = true;
						stringBuffer.delete(0, end + 1);
						return temp.toString();
					}
					continue;
				}
				if (stringBuffer.charAt(i) != '<') {
					temp.append(stringBuffer.charAt(i));
				}
			}// 如果当前读入的部分无法完成匹配，那么继续读入下一部分
			if (hasNext()) {
				stringBuffer.append(read());
			} else {
				stringBuffer.delete(0, end + 1);
				return temp.toString();
			}
		}
		stringBuffer.delete(0, end + 1);
		return temp.toString();
	}

	/**
	 * 获得流中的attributes集合
	 * 
	 * @param stringBuffer
	 * @return
	 */
	private Map<String, String> getAttributes(StringBuffer stringBuffer) {
		Map<String, String> attributes = new HashMap<String, String>();
		StringBuffer temp = new StringBuffer();
		boolean flag = false;
		String name = null;
		String value = null;
		int i = 0;
		while (flag == false) {
			for (; i < stringBuffer.length(); i++) {
				if (stringBuffer.charAt(i) == '\r'
					|| stringBuffer.charAt(i) == '\n'
					|| stringBuffer.charAt(i) == '\t') {
					continue;
				}
				if (stringBuffer.charAt(i) == '<') {
					return attributes;
				}
				if (stringBuffer.charAt(i) == '/' || stringBuffer.charAt(i) == '>') {
					value = temp.toString();
					temp.delete(0, temp.length());
					attributes.put(name, value);
					name = null;
					value = null;
					stringBuffer.delete(0, i + 1);
					return attributes;
				}
				if (stringBuffer.charAt(i) == ' ') {
					if (name != null) {
						value = temp.toString();
						temp.delete(0, temp.length());
						attributes.put(name, value);
						name = null;
						value = null;
					}
					continue;
				}
				if (stringBuffer.charAt(i) == '=') {
					name = temp.toString();
					temp.delete(0, temp.length());
					continue;
				}
				temp.append(stringBuffer.charAt(i));
			}
			if (hasNext()) {
				stringBuffer.append(read());
			} else {
				stringBuffer.delete(0, i + 1);
				return attributes;
			}
		}
		stringBuffer.delete(0, i + 1);
		return attributes;
	}

	/**
	 * 获得流中element的值
	 * 
	 * @param stringBuffer
	 * @return
	 */
	private String getValue(StringBuffer stringBuffer) {
		boolean flag = false;
		int i = 0;
		StringBuffer temp = new StringBuffer();
		while (flag == false) {
			for (; i < stringBuffer.length(); i++) {
				if (stringBuffer.charAt(i) == '\r'
					|| stringBuffer.charAt(i) == '\n'
					|| stringBuffer.charAt(i) == '\t') {
					continue;
				}
				if (stringBuffer.charAt(i) == '<') {
					stringBuffer.delete(0, i);
					return temp.toString();
				} else {
					temp.append(stringBuffer.charAt(i));
				}
			}
			if (hasNext()) {
				stringBuffer.append(read());
			} else {
				stringBuffer.delete(0, i);
				return temp.toString();
			}
		}
		stringBuffer.delete(0, i);
		return temp.toString();
	}

	/**
	 * 处理流中应当被忽略的tag
	 * 
	 * @param stringBuffer
	 */
	private void ignoreTag(StringBuffer stringBuffer) {
		if (stringBuffer.charAt(0) == '<' && stringBuffer.charAt(1) == '/') {
			boolean flag = false;
			int i = 2;
			while (flag == false) {
				for (; i < stringBuffer.length(); i++) {
					if (stringBuffer.charAt(i) == '>')
						stringBuffer.delete(0, i);
					return;
				}
				if (hasNext()) {
					stringBuffer.append(read());
				} else {
					stringBuffer.delete(0, i);
				}
			}
		}
	}

	// /////////////////////////////写操作相关////////////////////////////////////

	@Override
	public void setOutStream(OutputStream out) {
		channelOut = Channels.newChannel(out);
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			byteBuffer.put("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes("utf-8"));
			channelOut.write(byteBuffer);
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void put(Element element) {
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
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void putDone(Element element) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("</");
		buffer.append(element.getName());
		buffer.append(">");
		try {
			byteBuffer.put(buffer.toString().getBytes("utf-8"));
			channelOut.write(byteBuffer);
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// main方法，用来做简单的测试
	public static void main(String[] args) throws IOException, DocumentException {

		System.out.println("反序列化objectA_XStream.xml，文件大小为18KB");
		XBlinkXmlAdapter_1 xBlinkXmlAdapter = new XBlinkXmlAdapter_1();
		xBlinkXmlAdapter.setInputStream(new FileInputStream(new File("c:\\struts-config-pengjingbin.xml")));
		long a = System.nanoTime();
		while (true) {
			Element element = xBlinkXmlAdapter.next();
			if (element != null) {

			} else {
				break;
			}
		}
		System.out.println("XBlinkXmlAdapter:");
		System.out.println((System.nanoTime() - a)+"ns");
		a = System.nanoTime();
		Document document = new SAXReader().read(new File("c:\\struts-config-pengjingbin.xml"));
		System.out.println("Dom4j:");
		System.out.println((System.nanoTime() - a)+"ns");

		 ///////////////////////////////////////////////
		System.out.println("反序列化objectA_XBlink.xml，文件大小为1KB");
		xBlinkXmlAdapter.setInputStream(new FileInputStream(new File("c:\\objectA_XStream.xml")));
		a = System.nanoTime();
		while (true) {
			Element element = xBlinkXmlAdapter.next();
			if (element != null) {

			} else {
				break;
			}
		}
		System.out.println("XBlinkXmlAdapter:");
		System.out.println((System.nanoTime() - a)+"ns");
		a = System.nanoTime();
		document = new SAXReader().read(new File("c:\\joe.xml"));
		System.out.println("Dom4j:");
		System.out.println((System.nanoTime() - a)+"ns");

		// Element element = new Element();
		// Element element2 = new Element();
		// element.setName("objectA");
		// element2.setName("objectB");
		// XBlinkXmlAdapter xBlinkXmlAdapter = new XBlinkXmlAdapter();
		// xBlinkXmlAdapter.setOutStream(new FileOutputStream(new
		// File("c:\\aa.xml")));
		// long a = System.nanoTime();
		// for (int i = 0; i < 1000; i++) {
		// xBlinkXmlAdapter.put(element);
		// xBlinkXmlAdapter.put(element2);
		// xBlinkXmlAdapter.putDone(element);
		// xBlinkXmlAdapter.putDone(element);
		// }
		// System.out.println(System.nanoTime() - a);
	}
}