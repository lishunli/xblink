package org.xblink.adapter.impl;

import java.io.InputStream;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xblink.adapter.XMLAdapter;
import org.xblink.xml.XMLAttributeMap;
import org.xblink.xml.XMLDocument;
import org.xblink.xml.XMLNode;
import org.xblink.xml.XMLNodeList;
/**
 * TODO 
 *
 *
 * @author E-Hunter(xjf1986518@gmail.com)
 * @author pangwu86(pangwu86@gmail.com)
 *
 */
public class Dom4jXMLAdapter implements XMLAdapter {

	private Document document;
	@Override
	public XMLDocument getDocument(InputStream in) {
		SAXReader reader = new SAXReader();
		document = null;
		try {
			document = reader.read(in);
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		XMLDocument xmlDocument = new XMLDocument();
		xmlDocument.setDocument(document);
		return xmlDocument;
	}

	@Override
	public XMLNode getFirstChild(Object document) {
		Element element = (Element) this.document.getRootElement().elementIterator().next();
		XMLNode xmlNode = new XMLNode();
		xmlNode.setNode(element);
		return xmlNode;
	}

	@Override
	public String getNodeName(Object node) {
		Element element = (Element) node;
		return element.getName();
	}

	@Override
	public XMLNodeList getChildNodes(Object node) {
		Element element = (Element) node;
		XMLNodeList xmlNodeList = new XMLNodeList();
		xmlNodeList.setNodeList(element.elements());
		return xmlNodeList;
	}

	@Override
	public String getNodeValue(Object node) {
		Element element = (Element) node;
		return element.getText();
	}

	@Override
	public String getElementValue(Object node, String elementName) {
		Element element = (Element) node;
		return element.element(elementName).getText();
	}

	@Override
	public XMLAttributeMap getAttributes(Object node) {
		Element element = (Element) node;
		return null;
	}

	@Override
	public XMLNode getNextSibling(Object node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getXMLNodeListLength(Object nodeList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public XMLNode getNodeListItem(Object nodeList, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XMLNode getNamedItem(Object attributeMap, String objValue) {
		// TODO Auto-generated method stub
		return null;
	}
}
