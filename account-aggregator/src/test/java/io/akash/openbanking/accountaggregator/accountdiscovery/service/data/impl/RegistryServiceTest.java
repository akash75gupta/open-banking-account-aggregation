package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.FileUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RegistryEntityRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/**
 * A class containing unit tests for Registry Service.
 * 
 * @author Akash Gupta (akash75gupta@gmail.com)
 * @version 1.0
 * @since 2019-05-13
 * 
 **/

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegistryServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Mock
	RegistryEntityRepository registryEntityRepository;
	
	@InjectMocks
	RegistryService registryService;
	
	private static final String VALID_FIP_ID = "rbs";
	private static final String INVALID_FIP_ID = "abc";
	
	private static List<RegistryEntity> fipsFromRegistry;
	private static RegistryEntity 		 fipFromRegistry_Rbs;

	@BeforeClass
	public static void init() {
		prepareForGetFipsTest_FipsFound();
		prepareForGetFipTest_ValidFipId();
	}

	private static void prepareForGetFipTest_ValidFipId() {
		String registryEntity_fip_rbs_json = FileUtil.fetchTestDataForClass("registryEntity_fip_rbs.json",
				RegistryServiceTest.class.getSimpleName());

		try {
			fipFromRegistry_Rbs = MAPPER.readValue(registryEntity_fip_rbs_json, RegistryEntity.class);

		} catch (JsonParseException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (JsonMappingException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (IOException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		}
	}

	private static void prepareForGetFipsTest_FipsFound() {
		String registryEntity_fips_json = FileUtil.fetchTestDataForClass("registryEntity_fips.json",
				RegistryServiceTest.class.getSimpleName());

		try {
			fipsFromRegistry = MAPPER.readValue(registryEntity_fips_json,
					new TypeReference<ArrayList<RegistryEntity>>() {
					});

		} catch (JsonParseException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (JsonMappingException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (IOException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		}
	}

	@Test
	public void getFipsTest_FipsFound() {
		log.info("Testing getFip() functionality of RegistryService for a positive test case "
				+ "where fips are found/available in the DB.");
	
		List<RegistryEntity> expected = fipsFromRegistry;
		
		when(registryEntityRepository.findAll()).thenReturn(fipsFromRegistry);

		assertEquals(expected,registryService.getFips());
	}
	
	@Test(expected = CustomException.class)
	public void getFipsTest_FipsNotFound(){
		log.info("Testing getFips() functionality of RegistryService for a negetive test case "
				+ "where fips are not found.");
		
		when(registryEntityRepository.findAll()).thenReturn(null);
		registryService.getFips();
	}
	
	@Test
	public void getFipTest_ValidId(){
		log.info("Testing getFip() functionality of RegistryService for a positive test case.");
	
		RegistryEntity expected=fipFromRegistry_Rbs;

		when(registryEntityRepository.findByBusinessId(VALID_FIP_ID)).thenReturn(fipFromRegistry_Rbs);

		assertEquals(expected,registryService.getFip(VALID_FIP_ID));
	}

	@Test(expected = CustomException.class)
	public void getFipTest_InvalidId(){
		when(registryEntityRepository.findById(INVALID_FIP_ID)).thenReturn(null);
		registryService.getFip(INVALID_FIP_ID);
	}
}
