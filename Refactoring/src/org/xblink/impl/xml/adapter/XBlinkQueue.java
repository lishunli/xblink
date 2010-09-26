package org.xblink.impl.xml.adapter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * 
 * 解析XML使用的队列
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * 
 * @param <T>
 *            队列中可存放的数据类型
 */
public class XBlinkQueue {

	private LinkedList<Byte> stack = new LinkedList<Byte>();

	/**
	 * 测试队列是否为空
	 * 
	 * @return 返回一个表示堆栈是否为空的布尔值
	 */
	public boolean empty() {
		return stack.isEmpty();
	}

	/**
	 * 查看队列首部对象
	 * 
	 * @return 返回队列的第一个对象
	 */
	public Byte peek() {
		return stack.getFirst();
	}

	/**
	 * 移除队列首部对象
	 * 
	 * @return 删除队列的第一个对象
	 */
	public Byte pop() {
		if(stack.isEmpty()){
			return null;
		}
		Byte item = stack.getFirst();
		if (item != 0) {
			stack.removeFirst();
		}
		return item;
	}

	/**
	 * 把项追加至队列尾部
	 * 
	 * @param item
	 *            需要追加的项
	 * @return 被追加到队列尾部的对象
	 */
	public Byte push(Byte item) {
		stack.addLast(item);
		return item;
	}

	/**
	 * 返回对象在队列中的位置
	 * 
	 * @param item
	 *            需要查找的项
	 * @return 被查找项在队列中的位置
	 */
	public int search(Byte item) {
		int pos = stack.indexOf(item);
		return pos;
	}
	/**
	 * 返回队列的长度
	 * @return 队列的长度
	 */
	public int length(){
		return stack.size();
	}
	/**
	 * 将队列转换成字符串
	 * @return 转换完成的字符串
	 */
	public String toString(){
		byte[] bytes = new byte[length()]; 
		for(int i=0;i<length();i++){
			bytes[i] = stack.get(i);
		}
		String value = null;
		try {
			 value = new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clear();
		return value;
	}
	/**
	 * 清空队列
	 */
	public void clear(){
		stack.clear();
	}
}
