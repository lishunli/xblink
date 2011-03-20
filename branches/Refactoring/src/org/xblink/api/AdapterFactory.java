package org.xblink.api;

/**
 * 
 * XML适配器工厂类.
 * 
 * 
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * 
 */
public class AdapterFactory {
	private final static String XBLINK_XML_ADAPTER = "org.xblink.xml.adapter.XBlinkAdapter";
	/**
	 * 根据指定驱动程序获得适配器<br>
	 * 如果指定驱动器找不到适配器，则抛出异常
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Adapter getAdapter(String adapterName) throws Exception {
		if (adapterName.length()<=0) {
			throw new Exception("指定的适配器加载失败");
		}
		return (Adapter) Class.forName(adapterName).newInstance();
	}		
}

	