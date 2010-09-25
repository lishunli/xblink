package org.xblink.impl.xml.adapter;

import java.util.HashMap;
import java.util.Map;

import org.xblink.core.Element;
import org.xmlpull.mxp1.MXParser;

public class XBlinkAdapterUtils {

	public static Element setElementName(Element element, MXParser mxParser) {
		element.setName(mxParser.getName());
		return element;
	}

	public static Element setElementValue(Element element, MXParser mxParser) {
		element.setValue(mxParser.getText());
		return element;
	}

	public static Element setElementAttributes(Element element,
			MXParser mxParser) {
		Map<String, String> attributes = new HashMap<String, String>();
		for(int i=0;i<mxParser.getAttributeCount();i++){
			attributes.put(mxParser.getAttributeName(i), mxParser.getAttributeValue(i));
		}
		element.setAttributes(attributes);
		return element;
	}
}
