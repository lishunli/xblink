package org.xblink.core.cache;

import java.util.List;

/**
 * 缓存接口 
 * @author E-Hunter(xjf1986518@gmail.com)
 *
 * @param <T> 缓存类型
 */
public interface Cache<T> {
	/**
	 * 把一个对象放入缓存中
	 * @param key 对象在缓存中的key
	 * @param object 需要放入缓存的对象
	 */
	void put(Object key,Object object);
	/**
	 * 从缓存中取出一个对象
	 * @param t 需要取出对象的类型
	 * @param key 对象在缓存中的key
	 * @return 从缓存中取出的对象
	 */
	T get(T t,Object key);
	/**
	 * 将缓存中的对象转换成list
	 * @return
	 */
	List toList();
}
