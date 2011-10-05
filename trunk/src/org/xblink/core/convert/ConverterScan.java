package org.xblink.core.convert;

import java.util.ArrayList;
import java.util.List;

import org.xblink.core.convert.converters.BigDecimalConverter;
import org.xblink.core.convert.converters.BigIntegerConverter;
import org.xblink.core.convert.converters.BooleanConverter;
import org.xblink.core.convert.converters.ByteConverter;
import org.xblink.core.convert.converters.CharacterConverter;
import org.xblink.core.convert.converters.DateConverter;
import org.xblink.core.convert.converters.DoubleConverter;
import org.xblink.core.convert.converters.EnumConverter;
import org.xblink.core.convert.converters.FileConverter;
import org.xblink.core.convert.converters.FloatConverter;
import org.xblink.core.convert.converters.IntegerConverter;
import org.xblink.core.convert.converters.LongConverter;
import org.xblink.core.convert.converters.NullConverter;
import org.xblink.core.convert.converters.ShortConverter;
import org.xblink.core.convert.converters.StringBufferConverter;
import org.xblink.core.convert.converters.StringBuilderConverter;
import org.xblink.core.convert.converters.StringConverter;
import org.xblink.core.convert.converters.URIConverter;
import org.xblink.core.convert.converters.URLConverter;
import org.xblink.core.convert.converters.UUIDConverter;

/**
 * 自动扫描converters包下的所有转换器。
 * 
 * TODO 这里等下一个版本再进行改进吧
 * 
 * @author 胖五(pangwu86@gmail.com)
 */
public class ConverterScan {

	private static final String FLT_CLASS = "^.+[.]class$";

	private static final List<Class<?>> converterList = new ArrayList<Class<?>>();

	static {
		// 八种基本类型
		converterList.add(IntegerConverter.class);
		converterList.add(ShortConverter.class);
		converterList.add(ByteConverter.class);
		converterList.add(LongConverter.class);
		converterList.add(FloatConverter.class);
		converterList.add(DoubleConverter.class);
		converterList.add(BooleanConverter.class);
		converterList.add(CharacterConverter.class);
		// JAVA中常见类型
		converterList.add(NullConverter.class);
		converterList.add(EnumConverter.class);
		converterList.add(FileConverter.class);
		converterList.add(StringConverter.class);
		converterList.add(StringBuilderConverter.class);
		converterList.add(StringBufferConverter.class);
		converterList.add(DateConverter.class);
		converterList.add(BigDecimalConverter.class);
		converterList.add(BigIntegerConverter.class);
		converterList.add(URLConverter.class);
		converterList.add(URIConverter.class);
		converterList.add(UUIDConverter.class);
	}

	/**
	 * 搜索并返回给定包下所有的类（递归）
	 * 
	 * @param pkg
	 *            包名或者包路径
	 * @return
	 */
	public static List<Class<?>> scanPackage(String pkg) {
		return scanPackage(pkg, FLT_CLASS);
	}

	private static List<Class<?>> scanPackage(String pkg, String fltClass) {
		// TODO 改为自动扫描
		return converterList;
	}
}
