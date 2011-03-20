package org.xblink.api;

import org.xblink.core.Element;
/**
 * 
 * 扩展插件接口，用来对由XBlink封装后的Element进行二次封装和解封装
 * @author E-Hunter(xjf1986518@gmail.com)
 */
public interface Extension {

	/**
	 * 二次封装Element
	 * @param element 需要封装的Element
	 */
	public void pack(Element element );
	/**
	 * 需要解封装的Element
	 * @param element 需要解封的Element
	 * @return 解封装完成后的Element
	 */
	public Element unpack(Element element);
}
