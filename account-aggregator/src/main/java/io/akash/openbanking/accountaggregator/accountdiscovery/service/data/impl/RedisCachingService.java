package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.akash.openbanking.accountaggregator.accountdiscovery.controller.AccountDiscoveryController;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.CacheConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryRequestAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FipForAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.MapperUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RedisCounterRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RedisFipRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api.ICachingService;

/**
 * Purpose:- 
 * This is a caching service that directly interacts with custom Redis DAO classes
 * (also known as repositories). It provides an abstraction over the native CRUD operations on Redis.
 * 
 * 
 * @author Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since 2019-04-01
 * 
 **/

@Service
public class RedisCachingService implements ICachingService {

	private static final Logger log = LoggerFactory.getLogger(AccountDiscoveryController.class);
	
	@Autowired
	RedisFipRepository redisFipRepository;
	
	@Autowired
	RedisCounterRepository redisCounterRepository;
	
	@Autowired
	MapperUtil mapperUtil;

	@Override
	public Integer publishAccountDiscoveryResponse(AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO) {		
		log.info("Executing RedisCachingService.publishAccountDiscoveryResponse() "
				+"with Param 1 - AccountDiscoveryAppResponseDTO: {}"
				+"-Publishing(Saving) Account Discovery Response received from an FIP to cache ",accountDiscoveryAppResponseDTO);
		
		Integer numberOfRecordsUpdated = 0;
		
		List<FipForAppDTO> fipForAppList =  accountDiscoveryAppResponseDTO.getFipForAppList();
		
		String key = new StringBuilder()
					.append(CacheConstants.ACCOUNT_DISCOVERY_CACHE)
					.append(":")
					.append(accountDiscoveryAppResponseDTO.getCustomerId())
					.append(":")
					.append(accountDiscoveryAppResponseDTO.getTransactionId())
					.toString();
		
		updateCounter(accountDiscoveryAppResponseDTO);
		
		if(null != fipForAppList && !fipForAppList.isEmpty()) {
			for(FipForAppDTO fipForApp:fipForAppList) {
					FinancialInformationProvider fip = mapperUtil.mapFipForAppToFinancialInformationProvider(fipForApp);
						redisFipRepository.save(key, fip);
						numberOfRecordsUpdated++;
			}
		}
		
		log.info("Returning from RedisCachingService.publishAccountDiscoveryResponse() with result - "
				+ "numberOfRecordsUpdated(Integer): {} ",numberOfRecordsUpdated);
		
		return numberOfRecordsUpdated;
	}
	
	@Override
	public Integer updateCounter(AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO) {
		log.info("Executing RedisCachingService.updateCounter() "
				+ "with Param 1 - accountDiscoveryAppResponseDTO(AccountDiscoveryAppResponseDTO): {}"
				+ "-Updating the value of counter in cache on the basis of the number of FIPs explored(searched) ",
				accountDiscoveryAppResponseDTO);

		Integer updatedCounterValue = null;
		Integer existingCounterValue = null;

		String key = new StringBuilder().append(CacheConstants.ACCOUNT_DISCOVERY_COUNTER_CACHE).append(":")
				.append(accountDiscoveryAppResponseDTO.getCustomerId()).append(":")
				.append(accountDiscoveryAppResponseDTO.getTransactionId()).toString();

		existingCounterValue = (Integer) redisCounterRepository.getValueByKey(key);
	
		if (null == existingCounterValue) {
			updatedCounterValue = 1;
				redisCounterRepository.save(key, updatedCounterValue);
		} else {
			updatedCounterValue = ++existingCounterValue;
			redisCounterRepository.save(key, updatedCounterValue);
		}

		log.info("Returning from RedisCachingService.updateCounter() with result - "
				+ "updatedCounterValue(Integer): {} ", updatedCounterValue);

		return updatedCounterValue;
	}
	
	@Override
	public Integer fetchCountOfSearchedFips(AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO) {
		log.info("Executing RedisCachingService.fetchCountOfFipsSearched() "
				+ "with Param 1 - accountDiscoveryPollingAppRequestDTO(AccountDiscoveryPollingAppRequestDTO): {}"
				+ "-Fetching the count of FIPs explored(searched) from the cache ",
				accountDiscoveryPollingAppRequestDTO);

		Integer existingCounterValue = null;
		
		String key = new StringBuilder().append(CacheConstants.ACCOUNT_DISCOVERY_COUNTER_CACHE).append(":")
					.append(accountDiscoveryPollingAppRequestDTO.getCustomerId()).append(":")
					.append(accountDiscoveryPollingAppRequestDTO.getTransactionId()).toString();

		existingCounterValue = (Integer) redisCounterRepository.getValueByKey(key);

		log.info("Resulting from RedisCachingService.updateCounter() with result - existingCounterValue(Integer): {} ",
				existingCounterValue);
		
		return existingCounterValue;
	}
	
	@Override
	public List<FinancialInformationProvider> fetchAccountDiscoveryResponse
	(AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO) {
		log.info(
				"Executing RedisCachingService.fetchAccountDiscoveryResponse() "
						+ "with Param 1 - AccountDiscoveryPollingAppRequestDTO: {}"
						+ "- Fetching(Retrieving) Fips along wit their discovered accounts from cache ",
				accountDiscoveryPollingAppRequestDTO);

		List<FinancialInformationProvider> fetchedFips = null;

		String key = new StringBuilder().append(CacheConstants.ACCOUNT_DISCOVERY_CACHE).append(":")
				.append(accountDiscoveryPollingAppRequestDTO.getCustomerId()).append(":")
				.append(accountDiscoveryPollingAppRequestDTO.getTransactionId()).toString();

		fetchedFips = (List<FinancialInformationProvider>) redisFipRepository.getValueByKey(key);

		log.info("Returning from RedisCachingService.fetchAccountDiscoveryResponse() with value -  "
				+ "fetchedFips(List<FinancialInformationProvider>): {} ", fetchedFips);

		return fetchedFips;
	}
	
	@Override
	public Integer deleteAccountDiscoveryResponse(AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO, 
													List<FinancialInformationProvider> fips) {
		log.info("Executing RedisCachingService.deleteAccountDiscoveryResponse() "
						+ "with Param 1 - AccountDiscoveryPollingAppRequestDTO: {}"
						+ "-Publishing(Saving) Account Discovery Response received from an FIP to cache ",
				accountDiscoveryPollingAppRequestDTO);

		Integer numberOfRecordsDeleted = 0;

		String key = new StringBuilder().append(CacheConstants.ACCOUNT_DISCOVERY_CACHE).append(":")
				.append(accountDiscoveryPollingAppRequestDTO.getCustomerId()).append(":")
				.append(accountDiscoveryPollingAppRequestDTO.getTransactionId()).toString();

		for (FinancialInformationProvider fip : fips) {
			redisFipRepository.removeListElement(key, fip);
			numberOfRecordsDeleted++;
		}

		log.info("Returning from RedisCachingService.deleteAccountDiscoveryResponse() with result - "
				+ "numberOfRecordsDeleted(Integer): {} ", numberOfRecordsDeleted);

		return numberOfRecordsDeleted;
	}

	@Override
	public Integer cleanAccountDiscoveryCounterCache(
			AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryRequestAppDTO) {
		log.info("Executing RedisCachingService.cleanAccountDiscoveryCounterCache() "
						+ "with Param - autoAccountDiscoveryRequestAppDTO(AutoAccountDiscoveryRequestAppDTO): {}"
						+ "- Cleaning/Resetting the account discovery counter cache.",
				autoAccountDiscoveryRequestAppDTO);

		Integer numberOfRecordsDeleted = 0;

		String key = new StringBuilder().append(CacheConstants.ACCOUNT_DISCOVERY_COUNTER_CACHE).append(":")
				.append(autoAccountDiscoveryRequestAppDTO.getCustomerId()).append(":")
				.append(autoAccountDiscoveryRequestAppDTO.getTransactionId()).toString();

		redisCounterRepository.remove(key);
		numberOfRecordsDeleted++;

		log.info("Returning from RedisCachingService.cleanAccountDiscoveryCounterCache() with result - "
				+ "numberOfRecordsDeleted(Integer): {} ", numberOfRecordsDeleted);

		return numberOfRecordsDeleted;
	}

	@Override
	public Integer cleanAccountDiscoveryCache(
								AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryRequestAppDTO) {
		log.info("Executing RedisCachingService.cleanAccountDiscoveryCache() "
				+"with Param - autoAccountDiscoveryRequestAppDTO(AutoAccountDiscoveryRequestAppDTO): {}"
				+"- Cleaning/Resetting the account discovery cache.",
				autoAccountDiscoveryRequestAppDTO);
		
		Integer numberOfRecordsDeleted = 0;

		String key = new StringBuilder()
				.append(CacheConstants.ACCOUNT_DISCOVERY_CACHE)
				.append(":")
				.append(autoAccountDiscoveryRequestAppDTO.getCustomerId())
				.append(":")
				.append(autoAccountDiscoveryRequestAppDTO.getTransactionId())
				.toString();
		
		redisFipRepository.remove(key);
		numberOfRecordsDeleted++;
	
		log.info("Returning from RedisCachingService.cleanAccountDiscoveryCache() with result - "
				+ "numberOfRecordsDeleted(Integer): {} ",numberOfRecordsDeleted);
		
		return numberOfRecordsDeleted;
	}
}
