package org.xblink.writer;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.xblink.Constants;
import org.xblink.XMLObject;
import org.xblink.XRoot;
import org.xblink.transfer.ReferenceObject;
import org.xblink.transfer.TransferInfo;

/**
 * XML序列化工具类.
 * 
 * @author pangwu86(pangwu86@gmail.com)
 * 
 */
public class XMLWriter {

	/**
	 * 
	 * @param outputStream
	 * @param obj
	 * @throws Exception
	 */
	public static void writeXML(OutputStream outputStream, Object obj) {
		try {
			writeStart(outputStream, obj, true, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(Constants.SERIALIZE_FAIL, e);
		}
	}

	/**
	 * 
	 * @param out
	 * @param obj
	 * @param formatXml
	 * @param encoding
	 * @throws Exception
	 */
	private static void writeStart(OutputStream out, Object obj, boolean formatXml, String encoding) throws Exception {
		// 解析过的对象，方便其他对象引用
		Map<Integer, ReferenceObject> referenceObjects = new HashMap<Integer, ReferenceObject>();
		// 传递信息对象
		TransferInfo transferInfo = new TransferInfo();
		transferInfo.setReferenceObjects(referenceObjects);
		XMLWriterHelper writer = null;
		try {
			writer = new XMLWriterHelper(out, formatXml ? 2 : 1, encoding);
			Class<?> objClass = obj.getClass();
			XRoot root = new XRoot();
			// 判断是否是集合类型，是的话放入root对象中再进行序列化
			if (objClass.isArray()) {
				root.setArray((Object[]) obj);
				obj = root;
			} else if (XMLCollection.List.equals(getCollection(objClass))) {
				root.setList((List<?>) obj);
				obj = root;
			} else if (XMLCollection.Set.equals(getCollection(objClass))) {
				root.setSet((Set<?>) obj);
				obj = root;
			} else if (XMLCollection.Map.equals(getCollection(objClass))) {
				root.setMap((Map<?, ?>) obj);
				obj = root;
			}
			// 开始序列化
			writer.writeStartDocument();
			new XMLSerializer().serialize(obj, writer, null, transferInfo);
			writer.writeEndDocument();
		} finally {
			// 去掉XRoot的信息
			XMLObject.cleanXRoot();
			// 手动释放对象引用
			transferInfo = null;
			// 关闭相关流
			if (out != null) {
				out.close();
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 判断该Class是哪种集合类型.
	 * 
	 * @param clz
	 * @return
	 */
	private static XMLCollection getCollection(Class<?> clz) {
		Class<?>[] interfaces = clz.getInterfaces();
		for (Class<?> inter : interfaces) {
			if (inter.equals(XMLCollection.List.getClz())) {
				return XMLCollection.List;
			} else if (inter.equals(XMLCollection.Set.getClz())) {
				return XMLCollection.Set;
			} else if (inter.equals(XMLCollection.Map.getClz())) {
				return XMLCollection.Map;
			}
		}
		return XMLCollection.NotCollection;
	}

}
