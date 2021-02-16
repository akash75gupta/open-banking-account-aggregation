package io.akash.openbanking.accountaggregator.accountdiscovery.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/**
 * A DAO class that directly interacts with the Redis Cache 
 * and performs CRUD operations pertaining to a Financial Information User. 
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-19
 **/

@Repository
public class RedisFipRepository implements IRedisRepository{
	private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

	@Autowired
	private RedisTemplate<String, FinancialInformationProvider> redisTemplate;

	@Override
	public void save(String key, Object value) {
		FinancialInformationProvider fip = (FinancialInformationProvider)value;
		
		log.info("Executing RedisFipRepository.save() with Param 1 - key(String): {} -"
				+"and Param 2 - value(FinancialInformationProvider): {}"
				+"Adding FIP to the cache: {} to cache",key,fip);
		
		ListOperations<String, FinancialInformationProvider> listOps = redisTemplate.opsForList();
		redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		
		try {
			listOps.leftPush(key, fip);
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while caching information of fip- "+fip.getName())
					.append("against the key- "+key+" using lpush operation")
					.append(System.getProperty("line.separator"))
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA:Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString(), e);
		}
		log.info("Returning from RedisFipRepository.save() with void result");
	}

	@Override
	public Long getListSize(String key) {
		log.info("Executing RedisFipRepository.getNumberOfFips() for Param - key(String): {}"
				+"- Getting count of the values stored against the key", key);
		
		Long size = null;
		
		try {
			ListOperations<String, FinancialInformationProvider> listOps = redisTemplate.opsForList();
			size = listOps.size(key);
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while retrieving the size of the list containing fip information cached against the key- "+key)
					.append(System.getProperty("line.separator"))
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA:Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString(), e);
		}
		
		log.info("Returning from RedisFipRepository.getNumberOfFips()"
				+"with void result");
		
		return size;
	}

	@Override
	public Object getByIndex(String key, Integer index) {
		log.info("Executing RedisFipRepository.getFipAtIndex() with Param - Index: {}"
				+"Fetching FIP stored at the given index in the cache",index);
		
		FinancialInformationProvider fip = redisTemplate.opsForList().index(key, index);
		
		log.info("Return Executing RedisFipRepository.getFipAtIndex() -"
				+"Getting count of the number of FIPs in cache");
		
		return fip;
	}

	@Override
	public void removeListElement(String key, Object fip) {
		log.info("Executing RedisFipRepository.removeFip() with Param - FinancialInformationProvider: {}"
				+"Fetching FIP stored at the given index in the cache",fip);
		
		try {
			redisTemplate.opsForList().remove(key, 1, fip);
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while deleting the element- "+fip)
					.append("from the list cached against the key- "+key)
					.append(System.getProperty("line.separator"))
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA:Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString(), e);
		}
				
		log.info("Returning from RedisFipRepository.removeFip() -"
				+"Getting count of the number of FIPs in cache");
	}
	
	@Override
	public List<FinancialInformationProvider> getValueByKey(String key) {
		log.info("Executing RedisFipRepository.getAllFips() -"
				+"Fetching all the FIPs from cache for key: {}",key);
		
		List<FinancialInformationProvider> fips= redisTemplate.opsForList().range(key, 0, -1);
		
		log.info("Returning from RedisFipRepository.getAllFips() with value "
				+ "- List<FinancialInformationProvider>: {}",fips);
		
		return fips;
	}

	public void remove(String key) {
		log.info("Executing RedisFipRepository.remove() with Param - key(String): {}"
				+"Deleting the given key from cache",key);
		
		redisTemplate.opsForValue().getOperations().delete(key);
		
		log.info("Returning from RedisFipRepository.remove() with void result");
	}
}
