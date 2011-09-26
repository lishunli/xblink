package org.xblink.core.doc;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;

import org.xblink.core.doc.impl.XmlReader;
import org.xblink.core.doc.impl.XmlWriter;
import org.xblink.util.StringUtil;

/**
 * 生成DocReader与DocWriter的工厂。
 * 
 * @author 胖五(pangwu86@gmail.com)
 * 
 */
public class DocWorkerFactory {

	private DocWorkerFactory() {
	}

	private final static String WRITER_IMPL_CLASS_NAME = "org.xblink.core.doc.impl.%sWriter";

	private final static String READER_IMPL_CLASS_NAME = "org.xblink.core.doc.impl.%sReader";

	private static DocWriter findDocWriterByDocTypeName(Writer writer, String docTypeName) {
		return (DocWriter) getInstance(writer, Writer.class, docTypeName, WRITER_IMPL_CLASS_NAME);
	}

	private static DocReader findDocReaderByDocTypeName(Reader reader, String docTypeName) {
		return (DocReader) getInstance(reader, Reader.class, docTypeName, READER_IMPL_CLASS_NAME);
	}

	private static Object getInstance(Object initarg, Class<?> paramClz, String docTypeName, String implClassName) {
		String className = String.format(implClassName, StringUtil.upperFirstLowerOther(docTypeName));
		Constructor<?> constructor = getConstructor(className, paramClz);
		Object instance = null;
		try {
			instance = constructor.newInstance(initarg);
		} catch (Exception e) {
			throw new RuntimeException(String.format("[%s]无法进行实例化。", className), e);
		}
		return instance;
	}

	private static Constructor<?> getConstructor(String className, Class<?> paramClz) {
		Constructor<?> constructor = null;
		try {
			Class<?> clz = Class.forName(className);
			constructor = clz.getDeclaredConstructor(paramClz);
		} catch (Exception e) {
			throw new RuntimeException(String.format("没有找到[%s]这个实现类，无法执行后续操作。", className), e);
		}
		return constructor;
	}

	// ******************** 生成Writer ********************

	public static DocWriter createXmlWriter(Writer writer) {
		return new XmlWriter(writer);
	}

	public static DocWriter createAnyWriter(Writer writer, String docTypeName) {
		return findDocWriterByDocTypeName(writer, docTypeName);
	}

	// ******************** 生成Reader ********************

	public static DocReader createXmlReader(Reader reader) {
		return new XmlReader(reader);
	}

	public static DocReader createAnyReader(Reader reader, String docTypeName) {
		return findDocReaderByDocTypeName(reader, docTypeName);
	}
}
