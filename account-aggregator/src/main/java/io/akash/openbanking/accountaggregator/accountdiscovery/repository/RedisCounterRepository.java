package io.akash.openbanking.accountaggregator.accountdiscovery.repository;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.akash.openbanking.accountaggregator.accountdiscovery.controller.AccountDiscoveryController;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/**
 * A DAO class that directly interacts with the Redis Cache 
 * and performs CRUD operations pertaining to a counter 
 * that keeps a count of the number of FIPs explored during auto discovery. 
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-19
 **/

@Repository
@Transactional
public class RedisCounterRepository {
	
	private static final Logger log = LoggerFactory.getLogger(AccountDiscoveryController.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public void save(String key, Object value) {
		log.info("Executing RedisCounterRepository.save() with Param 1 - key(String): {} -"
				+"and Param 2 - value(Object): {}"
				+"Updating counter value in cache",key,value);
	
		redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		
		try {
			redisTemplate.opsForValue().set(key, value);
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while saving the fip counter value "+value+" cached against the key- "+key)
					.append(System.getProperty("line.separator"))
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA:Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString(), e);
		}
		
		log.info("Returning from RedisCounterRepository.save() with void result");
	}
	
	public Object getValueByKey(String key) {
		log.info("Executing RedisCounterRepository.getByKey() with Param - key(String): {}"
				+"Getting count of the number FIPs explored(serached) for discovering customer accounts",key);
		
		Integer counter = 0;
		
		try {
			Object value = redisTemplate.opsForValue().get(key);

			if (null != value) {
				counter = Integer.parseInt(value.toString());
			}
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while retrieving fip counter value cached against the key- "+key)
					.append(System.getProperty("line.separator"))
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA:Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString(), e);
		}
		
		log.info("Returning from RedisCounterRepository.getByKey() with result - counter(Integer): {}",counter);
		
		return counter;
	}

	public void remove(String key) {
		log.info("Executing RedisCounterRepository.remove() with Param - key(String): {}"
				+"Deleting the given key from cache",key);
		
		try {
			redisTemplate.opsForValue().getOperations().delete(key);
		}catch(Exception e){
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while removing the key- "+key+" from cache.")
					.append(System.getProperty("line.separator"))
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA:Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString(), e);
		}
		
		log.info("Returning from RedisCounterRepository.remove() with void result");
	}

}
