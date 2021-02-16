package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api;

/**
 * 
 * Purpose: 
 * All the caching services irrespective of their underlying vendor implementation 
 * e.g Redis, CouchBase etc should respect the contract defined by this interface
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-01
 * 
 */

public interface ICachingServiceDeprecated{

	Object getValueByKey(String cacheName, String key);

	void evictCacheByKey(String cacheName, String key);

	void putValueForKey(String cacheName, String key, Object value);

	void evict(String cacheName, String key, Object value);
}