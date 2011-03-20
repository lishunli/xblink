package org.xblink.core;

import java.util.HashMap;
import java.util.Map;
/**
 * XBlink基本操作单位
 * @author E-Hunter(xjf1986518@gmail.com)
 *
 */
public class Element {

	private String name;
	private Map<String, String> attributes = new HashMap<String, String>();
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
