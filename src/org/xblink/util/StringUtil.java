package org.xblink.util;

/**
 * 与字符串相关的一些常用方法。<BR>
 * 
 * 这里参照了Nutz的一部分实现。
 * 
 * @author 胖五(pangwu86@gmail.com)
 * 
 */
public abstract class StringUtil {

	/**
	 * 判断是否是空字符串
	 * 
	 * @param cs
	 *            字符串
	 * @return 是不是为空白字符串
	 */
	public static boolean isBlankStr(CharSequence cs) {
		if (null == cs)
			return true;
		int length = cs.length();
		for (int i = 0; i < length; i++) {
			if (!(Character.isWhitespace(cs.charAt(i))))
				return false;
		}
		return true;
	}

	/**
	 * 将字符串首字母大写，其他小写
	 * 
	 * @param s
	 *            字符串
	 * @return 首字母大写后的新字符串
	 */
	public static String upperFirstLowerOther(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		StringBuilder sb = new StringBuilder(len).append(Character.toUpperCase(s.charAt(0)));
		for (int i = 1; i < len; i++) {
			char ch = s.charAt(i);
			if (Character.isUpperCase(ch)) {
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
}
