package org.xblink.impl.xml.adapter;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.xblink.api.Adapter;
import org.xblink.core.Element;
;

public class XBlinkXmlAdapter implements Adapter {
	//特殊字符标记状态
	private State tagStartState;
	private State tagEndState;
	private State blankState;
	private State equleState;
	private State slashState;
	//状态机
	private StateMachine stateMachine = new StateMachine(2);
	//是否字面值
	private boolean isLiteral=false;
	// 文件指针，用来标识当前读取位置
	int size = 0;
	// 读取channel
	private ReadableByteChannel channelIn = null;
	// 写入channel
	private WritableByteChannel channelOut = null;
	int buffSize = 1024;
	byte[] bytes = new byte[buffSize];
	ByteBuffer byteBuffer = ByteBuffer.allocate(buffSize);
	//缓存
	private Element element = new Element();
	
	private XBlinkQueue<Character> queue = new XBlinkQueue<Character>();
	
	public XBlinkXmlAdapter(){
		//初始化状态标记
		tagStartState = new State('<');
		tagStartState.setAction(new ActionCallBack() {
			@Override
			public void run() {
				if(stateMachine.getState(0)==tagEndState){
				}
			}
		});
		
		tagEndState = new State('>');
		tagEndState.setAction(new ActionCallBack() {
			
			@Override
			public void run() {
			// TODO Auto-generated method stub
			
			}
		});
		
		blankState = new State(' ');
		blankState.setAction(new ActionCallBack() {
			
			@Override
			public void run() {
			// TODO Auto-generated method stub
			
			}
		});
		
		equleState= new State('=');
		equleState.setAction(new ActionCallBack() {
			
			@Override
			public void run() {
			// TODO Auto-generated method stub
			
			}
		});
		
		slashState = new State('/');
		slashState.setAction(new ActionCallBack() {
			
			@Override
			public void run() {
			// TODO Auto-generated method stub
			
			}
		});
	}
	
	@Override
	public void setInputStream(InputStream in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOutStream(OutputStream out) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Element find(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String put(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String putDone(Element element) {
		// TODO Auto-generated method stub
		return null;
	}
}
