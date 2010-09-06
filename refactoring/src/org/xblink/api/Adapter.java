package org.xblink.api;

import java.io.InputStream;

import org.xblink.core.Element;
import org.xblink.xml.XMLAttributeMap;
import org.xblink.xml.XMLDocument;
import org.xblink.xml.XMLNode;
import org.xblink.xml.XMLNodeList;

/**
 * XML解析器接口.
 * 
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * @author pangwu86(pangwu86@gmail.com)
 * 
 */
public interface Adapter {

	public void setDocument(InputStream in);

	public Element next();

	public Element find(String prefix);

}
