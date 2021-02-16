package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.FileUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.MapperUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RedisCounterRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RedisFipRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisCachingServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

	@Autowired
	RedisCachingService redisCachingService;

	@MockBean
	RedisFipRepository redisFipRepository;
	
	@MockBean
	RedisCounterRepository redisCounterRepository;
	
	@MockBean
	MapperUtil mapperUtil;
	
	private static final String ACCOUNT_DISCOVERY_CACHE_KEY 		= "accountDiscoveryCache:rithvik.pamidi@abcfinsoft:4a4adbbe-29ae-11e8-a8d7-0289437bf331";
	private static final String ACCOUNT_DISCOVERY_COUNTER_CACHE_KEY = "accountDiscoveryCounterCache:rithvik.pamidi@abcfinsoft:4a4adbbe-29ae-11e8-a8d7-0289437bf331";

	private static AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO;
	private static List<FinancialInformationProvider> fips;
	private static AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO;

	@BeforeClass
	public static void preparePublishAccountDiscoveryResponseTest() throws Exception {
		log.info("Executing RedisCachingServiceTest.preparePublishAccountDiscoveryResponseTest()");
		
		String testDataForAccountDiscoveryAppResponseDTO = FileUtil.fetchTestDataForClass(
				"accountDiscoveryAppResponseDTO.json",RedisCachingServiceTest.class.getSimpleName());

		accountDiscoveryAppResponseDTO = new Gson().fromJson(
				testDataForAccountDiscoveryAppResponseDTO, AccountDiscoveryAppResponseDTO.class);
		
		log.info("Executing RedisCachingServiceTest.prepareDeleteAccountDiscoveryResponseTest()");

		String testDataForAccountDiscoveryPollingAppRequestDTO = FileUtil.fetchTestDataForClass(
				"accountDiscoveryPollingAppRequestDTO.json",RedisCachingServiceTest.class.getSimpleName());

		accountDiscoveryPollingAppRequestDTO = new Gson().fromJson(
				testDataForAccountDiscoveryPollingAppRequestDTO, 
													AccountDiscoveryPollingAppRequestDTO.class);
		
		String testDataForFips = FileUtil.fetchTestDataForClass(
				"fips.json",RedisCachingServiceTest.class.getSimpleName());
		
		fips = new Gson().fromJson(
				testDataForFips, new TypeToken<ArrayList<FinancialInformationProvider>>() {
				}.getType());
	}
	
	@Test
	public void publishAccountDiscoveryResponseTest_Positive() {
		log.info("Executing RedisCachingServiceTest.publishAccountDiscoveryResponseTest_Positive()");
		
		Integer expected = Integer.valueOf(accountDiscoveryAppResponseDTO.getFipForAppList().size());
		
		doNothing().when(redisFipRepository).save(ACCOUNT_DISCOVERY_CACHE_KEY, fips.get(0));
		doReturn(fips.get(0)).when(mapperUtil).mapFipForAppToFinancialInformationProvider(accountDiscoveryAppResponseDTO.getFipForAppList().get(0));
		
		assertEquals(expected, 
				redisCachingService.publishAccountDiscoveryResponse(accountDiscoveryAppResponseDTO));
	}
	
	@Test
	public void updateCounterTest_Positive() {
		log.info("Executing RedisCachingServiceTest.updateCounterTest_Positive()");
		
		doReturn(Integer.valueOf(1)).when(redisCounterRepository).getValueByKey(ACCOUNT_DISCOVERY_COUNTER_CACHE_KEY);
		
		doNothing().when(redisCounterRepository).save(ACCOUNT_DISCOVERY_COUNTER_CACHE_KEY, Integer.valueOf(2));

		assertEquals(Integer.valueOf(2),redisCachingService.updateCounter(accountDiscoveryAppResponseDTO));
	}
	
	@Test
	public void fetchCountOfSearchedFipsTest_Positive() {
		log.info("Executing RedisCachingServiceTest.fetchCountOfSearchedFipsTest_Positive()");
		
		doReturn(Integer.valueOf(2)).when(redisCounterRepository).getValueByKey(ACCOUNT_DISCOVERY_COUNTER_CACHE_KEY);
				
		assertEquals(Integer.valueOf(2),redisCachingService.fetchCountOfSearchedFips(accountDiscoveryPollingAppRequestDTO));
	}
	
	@Test
	public void fetchAccountDiscoveryResponseTest_Positive(){
		log.info("Executing RedisCachingServiceTest.fetchAccountDiscoveryResponseTest_Positive()");

		doReturn(fips).when(redisFipRepository).getValueByKey(ACCOUNT_DISCOVERY_CACHE_KEY);
		
		assertEquals(fips,redisCachingService.fetchAccountDiscoveryResponse(accountDiscoveryPollingAppRequestDTO));
	}
	
	@Test
	public void deleteAccountDiscoveryResponseTest_Positive() {
		log.info("Executing RedisCachingServiceTest.deleteAccountDiscoveryResponseTest_Positive()");
		
		doNothing().when(redisFipRepository).removeListElement(ACCOUNT_DISCOVERY_CACHE_KEY, fips.get(0));
		
		assertEquals(Integer.valueOf(fips.size()), redisCachingService.deleteAccountDiscoveryResponse(accountDiscoveryPollingAppRequestDTO,
																					fips));		
	}
}
