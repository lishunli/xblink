package org.xblink.writer;

import java.lang.reflect.Field;
import java.util.Map;

import org.xblink.AnalysisedClass;
import org.xblink.Constants;
import org.xblink.ReferenceObject;
import org.xblink.XMLObject;
import org.xblink.XType;
import org.xblink.annotations.XBlinkNotSerialize;
import org.xblink.util.ClassUtil;

/**
 * XML类对象序列化操作类.
 * 
 * 
 * @author pangwu86(pangwu86@gmail.com)
 * 
 */
public class XMLObjectWriter extends XMLObject {

	/**
	 * 对象为单位写入.
	 * 
	 * @param obj
	 * @param writer
	 * @param objectName
	 * @throws Exception
	 */
	public void write(Object obj, XMLWriterUtil writer, String objectName,
			Map<Integer, ReferenceObject> referenceObjects) throws Exception {
		// 记录解析过的Object
		ReferenceObject ref = referenceObjects.get(obj.hashCode());
		// 引用对象，特殊处理
		if (null != ref) {
			String startElement = null;
			if (objectName != null) {
				startElement = objectName;
			} else {
				startElement = ClassUtil.getClassName(obj.getClass()).toString();
			}
			writer.writeStartElement(startElement);
			// 调用toString
			writer.writeAttribute(Constants.OBJ_REFERENCE, String.valueOf(ref.getNo()));
			writer.writeEndElement();
			return;
		}
		// 记录解析过的Class
		AnalysisedClass analysisedClass;
		String className = obj.getClass().getName();
		analysisedClass = xmlWriteClasses.get(className);
		if (null == analysisedClass) {
			analysisedClass = new AnalysisedClass();
			analysisedClass.setXmlObject(this);
			xmlWriteClasses.put(className, analysisedClass);
			Field[] fields = obj.getClass().getDeclaredFields();
			// 将所有变量进行分类
			boolean isXBlinkClass = false;
			for (Field field : fields) {
				field.setAccessible(true);
				// 是否需要序列化
				XBlinkNotSerialize xNotSerialize = field.getAnnotation(XBlinkNotSerialize.class);
				if (null != xNotSerialize) {
					continue;
				}
				// 各种类型数据是否需要序列化
				init();
				for (XType xtype : xTypes) {
					if (xtype.getAnnotation(field)) {
						isXBlinkClass = true;
						break;
					}
				}
			}
			analysisedClass.setXBlinkClass(isXBlinkClass);
		}
		// XBlink序列化对象
		if (analysisedClass.isXBlinkClass()) {
			// 记录该对象，保持对其引用
			ReferenceObject refObject = new ReferenceObject();
			refObject.setNo(referenceObjects.size() + 1);
			refObject.setRef(obj);
			referenceObjects.put(obj.hashCode(), refObject);
			String startElement = null;
			if (objectName != null) {
				startElement = objectName;
			} else {
				startElement = ClassUtil.getClassName(obj.getClass()).toString();
			}
			writer.writeStartElement(startElement);
			// 开始进行分类处理
			for (XType xtype : analysisedClass.getXmlObject().getXTypes()) {
				if (xtype.isFieldsEmpty()) {
					continue;
				}
				xtype.writeItem(obj, writer, referenceObjects);
			}
		} else {
			writer.writeStartElement(obj.getClass().toString()
					.replaceAll(Constants.SPACE, Constants.UNDERLINE));
			// 调用toString
			writer.writeAttribute(Constants.OBJ_VALUE, obj.toString());
		}
		writer.writeEndElement();
	}
}