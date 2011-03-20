package org.xblink.core.cache;

import java.util.HashMap;
import java.util.Map;

import org.xblink.core.cache.impl.PackageScannerCache;
/**
 * 缓存池
 * @author E-Hunter(xjf1986518@gmail.com)
 *
 */
public class CachePool {

	private static Map<String, Cache> cacheMap = new HashMap<String, Cache>();
	/**
	 * 从缓存池中取出缓存
	 * @param cacheName 缓存名称
	 * @return 从缓存池中取出的缓存
	 */
	public static Cache getCache(String cacheName){
		return cacheMap.get(cacheName);
	}
	/**
	 * 将缓存放入缓存池
	 * @param cacheName 缓存在缓存池中的名称
	 * @param cache 需要放入缓存池的缓存
	 */
	public static void setCache(String cacheName, Cache cache){
		cacheMap.put(cacheName, cache);
	}
	/**
	 * 清除缓存池
	 */
	public static void clearAll(){
		cacheMap.clear();
	}
	/**
	 * 清除缓存池中的指定缓存
	 * @param key 需要被清除的缓存的名称
	 */
	public static void clear(String cachceName){
		cacheMap.remove(cachceName);
	}
}
