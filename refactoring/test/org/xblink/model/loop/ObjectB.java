package org.xblink.model.loop;

import org.xblink.api.annotations.XBlinkAlias;
import org.xblink.api.annotations.XBlinkAsAttribute;
import org.xblink.api.annotations.XBlinkAsObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XBlinkAlias("objectB")
@XStreamAlias("objectB")
public class ObjectB {

	@XBlinkAsObject
	private ObjectA objectA;

	@XBlinkAsAttribute
	@XStreamAsAttribute
	private String strB;

	public String getStrB() {
		return strB;
	}

	public void setStrB(String strB) {
		this.strB = strB;
	}

	public ObjectA getObjectA() {
		return objectA;
	}

	public void setObjectA(ObjectA objectA) {
		this.objectA = objectA;
	}

}
