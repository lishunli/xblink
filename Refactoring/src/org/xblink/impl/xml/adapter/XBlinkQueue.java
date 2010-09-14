package org.xblink.impl.xml.adapter;

import java.util.LinkedList;

/**
 * 
 * 解析XML使用的队列
 * @author E-Hunter(xjf1986518@gmail.com)
 *
 * @param <T> 队列中可存放的数据类型
 */
public class XBlinkQueue<T> {

	private LinkedList<T> stack = new LinkedList<T>();
	
	/**
	 * 测试队列是否为空
	 * @return 返回一个表示堆栈是否为空的布尔值
	 */
	public boolean empty(){
		return stack.isEmpty();
	}
	/**
	 * 查看队列首部对象
	 * @return 返回队列的第一个对象
	 */
	public T peek(){
		return stack.getFirst();
	}
	/**
	 * 移除队列首部对象
	 * @return 删除队列的第一个对象
	 */
	public T pop(){
		T item = stack.getFirst();
		stack.removeFirst();
		return item;
	}
	/**
	 * 把项追加至队列尾部
	 * @param item 需要追加的项
	 * @return 被追加到队列尾部的对象
	 */
	public T push(T item){
		stack.addLast(item);
		return item;
	}
	/**
	 * 返回对象在队列中的位置
	 * @param item 需要查找的项
	 * @return 被查找项在队列中的位置
	 */
	public int search(T item){
		int pos = stack.indexOf(item);
		return pos;
	}
}
