package org.xblink.impl.xml.adapter;

/**
 * 
 * 状态机，根据状态的转换情况调用其相应的回调方法
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * 
 */
public class StateMachine {

	private int capacity = 0;
	private int pos = 0;
	private State[] states;

	public StateMachine() {

	}

	public StateMachine(int capacity) {
		this.capacity = capacity;
		this.states = new State[capacity];
	}

	public StateMachine(State[] states) {
		this.states = states;
	}

	/**
	 * 设置状态机的容量
	 * 
	 * @param capacity
	 *            状态机的容量
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
		this.states = new State[capacity];
	}

	/**
	 * 获取状态机的容量
	 * 
	 * @return 状态机的容量
	 */
	public int getCapacity() {
		return this.states.length;
	}

	/**
	 * 向状态机增加新的状态
	 * 
	 * @param state
	 *            需要增加的状态
	 */
	public void addState(State state) {
		if (pos == capacity) {
			pos = 0;
	
		}
		states[pos++] = state;
	}

	/**
	 * 获取状态机中指定的状态
	 * 
	 * @param i
	 *            指定状态的位置
	 * @return 指定的状态
	 */
	public State getState(int i) {
		return states[i];
	}

	/**
	 * 重置状态机
	 */
	public void reset() {
		pos = 0;
	}
	/**
	 * 取得状态机中状态数量
	 * @return 状态机中状态的数量
	 */
	public int size(){
		return pos;
	}
}
