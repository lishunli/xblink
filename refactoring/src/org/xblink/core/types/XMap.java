package org.xblink.core.types;

import java.lang.reflect.Field;

import org.xblink.core.XType;
import org.xblink.core.transfer.TransferInfo;
import org.xblink.core.writer.XMLWriterHelper;
import org.xblink.xml.XMLNode;

/**
 * Map集合类型.
 * 
 * 
 * @author pangwu86(pangwu86@gmail.com)
 * 
 */
public class XMap extends XType {

	public boolean getAnnotation(Field field) {
		return false;
	}

	public void writeItem(Object obj, XMLWriterHelper writer, TransferInfo transferInfo)
			throws Exception {
		throw new Exception("未实现的功能.");
	}

	public void readItem(Object obj, XMLNode baseNode, TransferInfo transferInfo) throws Exception {
		throw new Exception("未实现的功能.");
	}
}
