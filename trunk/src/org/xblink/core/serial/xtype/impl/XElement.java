package org.xblink.core.serial.xtype.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.xblink.core.AnalysisObject;
import org.xblink.core.TransferInfo;
import org.xblink.core.convert.ConverterWarehouse;
import org.xblink.core.serial.xtype.XBasicType;

/**
 * Element类型。
 * 
 * @author 胖五(pangwu86@gmail.com)
 */
public class XElement extends XBasicType {

	public static final XElement INSTANCE = new XElement();

	public void writeBasicType(Object obj, AnalysisObject analysisObject, TransferInfo transferInfo, Object fieldValue,
			String tagName, Field field) throws Exception {
		String fieldValueStr = ConverterWarehouse.getTextValueByData(fieldValue.getClass(), fieldValue);
		transferInfo.getDocWriter().writeElementText(tagName, fieldValueStr);
	}

	public List<Field> getFields(AnalysisObject analysisObject) {
		return analysisObject.getElementFieldTypes();
	}
}
