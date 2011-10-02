package org.xblink.core.convert.converters;

import org.xblink.core.convert.Converter;

/**
 * short与其包装类转换器。
 * 
 * @author 胖五(pangwu86@gmail.com)
 */
public class ShortConverter implements Converter {

	public Class<?>[] getTypes() {
		return new Class<?>[] { short.class, Short.class };
	}

	public boolean canConvert(Class<?> type) {
		return short.class == type || Short.class == type;
	}

	public String obj2Text(Object obj) throws Exception {
		return obj.toString();
	}

	public Object text2Obj(String text) throws Exception {
		return Short.valueOf(text);
	}

}
