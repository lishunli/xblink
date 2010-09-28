package org.xblink.impl.xml.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.xblink.api.Adapter;
import org.xblink.core.Element;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParserException;

public class XPPAdapter implements Adapter {

	private MXParser mxParser = new MXParser();
	private Element element = null;
	private int hierarchy = 0;
	private boolean isdone = false;

	@Override
	public Element next() {
		isdone = false;
		try {
			while (isdone == false) {
				int next = mxParser.next();
				if (next == mxParser.START_TAG) {
					if (element != null) {
						Element temp = element;
						element = null;
						return temp;
					}
					hierarchy++;
					element = new Element();
					XBlinkAdapterUtils.setElementName(element, mxParser);
					XBlinkAdapterUtils.setElementAttributes(element, mxParser);
				}
				if (element != null && next == mxParser.TEXT) {
					// System.out.println(mxParser.getText());
					XBlinkAdapterUtils.setElementValue(element, mxParser);
					Element temp = element;
					element = null;
					return temp;
				}
				if (next == mxParser.END_TAG) {
					hierarchy--;
				}
				// System.out.println(mxParser.getText());
			}
		} catch (XmlPullParserException e) {
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return element;
	}

	@Override
	public String put(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String putDone(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInputStream(InputStream in) {
		InputStreamReader reader = new InputStreamReader(in);
		try {
			mxParser.setInput(reader);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void setOutStream(OutputStream out) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1; i++) {
			System.out.println("反序列化BigXML_XBlink.xml，文件大小为79.1MB");
			XPPAdapter xppAdapter = new XPPAdapter();
			xppAdapter.setInputStream(new FileInputStream(new File(
					"d:\\BigXML_XBlink.xml")));
			long a = System.nanoTime();
			int count = 0;
			while (true) {
				count++;
				Element element = xppAdapter.next();
				if (element != null) {
					System.out.println(element.getName());
					System.out.println(element.getAttributes());
					System.out.println(element.getValue());
					System.out.println(xppAdapter.getHierarchy());
				} else {
					break;
				}
			}
			System.out.println("XppXmlAdapter:");
			System.out.println((System.nanoTime() - a) + "ns,共解析" + count
					+ "个元素");
			a = System.nanoTime();
			Document document = new SAXReader().read(new File(
					"d:\\BigXML_XBlink3.xml"));
			System.out.println("Dom4j:");
			System.out.println((System.nanoTime() - a) + "ns");
		}
	}

	@Override
	public int getHierarchy() {
		return hierarchy;
	}

}
