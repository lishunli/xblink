package org.xblink.api;

import java.io.InputStream;
import java.io.OutputStream;

import org.xblink.core.Element;

/**
 * XML解析器接口.
 * 
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * @author pangwu86(pangwu86@gmail.com)
 * 
 */
public interface Adapter {

	/**
	 *  设置适配器读取的InputStream
	 * @param in 适配器读取的流
	 */
	public void setInputStream(InputStream in);
	/**
	 * 设置适配器写入的OutputStream
	 * @param out 适配器输出的流
	 */
	public void setOutStream(OutputStream out);
	/**
	 * 获得下一个Element
	 * @return 获得读取的Element
	 */
	public Element next();

	/**
	 * 根据前缀查找Element
	 * @param prefix 前缀
	 * @return 根据前缀找到的Element
	 */
	public Element find(String prefix);

	/**
	 * 向流中写入一个元素
	 * @param element 需要写入到流的Element
	 */
	public void put(Element element);
	/**
	 * 向流中声明写入element完毕
	 * @param element 写入完成的element
	 */
	public void putDone(Element element);
}
