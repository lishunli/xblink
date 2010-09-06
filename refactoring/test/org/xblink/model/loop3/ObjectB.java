package org.xblink.model.loop3;

import org.xblink.api.annotations.XBlinkAlias;
import org.xblink.api.annotations.XBlinkAsObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XBlinkAlias("objectB")
@XStreamAlias("objectB")
public class ObjectB {
	@XBlinkAsObject
	private ObjectC objectC;

	public ObjectC getObjectC() {
		return objectC;
	}

	public void setObjectC(ObjectC objectC) {
		this.objectC = objectC;
	}
	
}
