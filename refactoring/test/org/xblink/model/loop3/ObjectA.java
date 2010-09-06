package org.xblink.model.loop3;

import org.xblink.api.annotations.XBlinkAlias;
import org.xblink.api.annotations.XBlinkAsAttribute;
import org.xblink.api.annotations.XBlinkAsObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XBlinkAlias("objectA")
@XStreamAlias("objectA")
public class ObjectA {
	@XBlinkAsAttribute
	@XStreamAsAttribute
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
