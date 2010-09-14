package org.xblink.impl.xml.adapter;

/**
 * 状态封装类
 * @author E-Hunter(xjf1986518@gmail.com)
 *
 */
public class State {

	private ActionCallBack actionCallBack;
	private char mark;
	public State(char mark){
		this.mark=mark;
	}
	public State(){
	
	}
	/**
	 * 获得状态标志
	 * @return
	 */
	public char getMark() {
		return mark;
	}
	/**
	 * 设置状态标志
	 * @param mark 状态标志
	 */
	public void setMark(char mark) {
		this.mark = mark;
	}
	/**
	 * 设置状态被激活时的回调方法
	 * @param actionCallBack
	 */
	public void setAction(ActionCallBack actionCallBack){
		this.actionCallBack = actionCallBack;
	}
	/**
	 * 运行状态的回调方法
	 */
	public void runAction(){
		this.actionCallBack.run();
	}
}
