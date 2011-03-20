package org.xblink.api;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * XBlink(吾爱跳刀),XML序列化反序列化工具集.<BR/>
 * 支持基本类型，对象类型，数组类型，List，Set，Map等集合类型.
 * 
 * @author E-Hunter(xjf1986518@gmail.com)
 * @author pangwu86(pangwu86@gmail.com)
 */
public class XBlink {

	/**
	 * 序列化一个对象，生成XML文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param obj
	 *            实例对象
	 */
	public static void serialize(String filePath, Object obj,Adapter adapter) {

	}


	/**
	 * 序列化一个对象，生成XML文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param obj
	 *            实例对象
	 * @param formatXml
	 *            是否采用缩进格式
	 * @param encoding
	 *            文件编码
	 */
	public static void serialize(String filePath, Object obj, boolean formatXml, String encoding,Adapter adapter) {

	}

	/**
	 * 序列化一个对象，生成XML文件.
	 * 
	 * @param outputStream
	 *            包含文件信息的输出流
	 * @param obj
	 *            实例对象
	 */
	public static void serialize(OutputStream outputStream, Object obj,Adapter adapter) {

	}


	/**
	 * 序列化一个对象，生成XML文件.
	 * 
	 * @param outputStream
	 *            包含文件信息的输出流
	 * @param obj
	 *            实例对象
	 * @param formatXml
	 *            是否采用缩进格式
	 * @param encoding
	 *            文件编码
	 */
	public static void serialize(OutputStream outputStream, Object obj, boolean formatXml,
			String encoding,Adapter adapter) {

	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param clz
	 *            生成对象的Class
	 * @return 对象
	 */
	public static Object deserialize(String filePath, Class<?> clz, Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param clz
	 *            生成对象的Class
	 * @param implClasses
	 *            生成对象中包含的所有接口实现类
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(String filePath, Class<?> clz, Class<?>[] implClasses
	                                 ,Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param inputStream
	 *            包含文件信息的输 入流
	 * @param clz
	 *            生成对象的Class
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(InputStream inputStream, Class<?> clz,Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param inputStream
	 *            包含文件信息的输 入流
	 * @param clz
	 *            生成对象的Class
	 * @param implClasses
	 *            生成对象中包含的所有接口实现类
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(InputStream inputStream, Class<?> clz, Class<?>[] implClasses,
	                                 Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param clz
	 *            生成对象的Class
	 * @param classLoader
	 *            用户的类加载器
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(String filePath, Class<?> clz, ClassLoader classLoader,Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param clz
	 *            生成对象的Class
	 * @param implClasses
	 *            生成对象中包含的所有接口实现类
	 * @param classLoader
	 *            用户的类加载器
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(String filePath, Class<?> clz, Class<?>[] implClasses,
			ClassLoader classLoader,Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param inputStream
	 *            包含文件信息的输 入流
	 * @param clz
	 *            生成对象的Class
	 * @param classLoader
	 *            用户的类加载器
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(InputStream inputStream, Class<?> clz, ClassLoader classLoader,
	                                 Adapter adapter) {
		return null;
	}


	/**
	 * 反序列化一个对象，源自XML文件.
	 * 
	 * @param inputStream
	 *            包含文件信息的输 入流
	 * @param clz
	 *            生成对象的Class
	 * @param implClasses
	 *            生成对象中包含的所有接口实现类
	 * @param classLoader
	 *            用户的类加载器
	 * @param domDriver
	 *            DOM驱动
	 * @return 对象
	 */
	public static Object deserialize(InputStream inputStream, Class<?> clz, Class<?>[] implClasses,
			ClassLoader classLoader,Adapter adapter) {
				return null;

	}

	/**
	 * 清空当前环境中的缓存.
	 */
	public static void cleanCache() {

	}
}
