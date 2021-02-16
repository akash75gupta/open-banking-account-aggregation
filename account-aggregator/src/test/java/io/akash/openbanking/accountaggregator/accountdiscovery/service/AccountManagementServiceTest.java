package io.akash.openbanking.accountaggregator.accountdiscovery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryRequestAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryResponseAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.DiscoveredAccountFipDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.DiscoveredAccountForAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FipDetailsForFipDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FipForAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.FileUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.MapperUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.IdentifierSpecification;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.UserAccount;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.IdentifierSpecificationRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.UserAccountRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl.CentralRegistryClientService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl.FipClientService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl.RedisCachingService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl.RegistryService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/**
 * A class containing unit tests for Account Management Service.
 * 
 * @author Akash Gupta (akash75gupta@gmail.com)
 * @author 
 * @version 1.0
 * @since 2019-03-27
 * 
 **/

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountManagementServiceTest {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Mock
	IdentifierSpecificationRepository identifierSpecificationRepository;

	@Mock
	RegistryService registryService;

	@Mock
	private UserAccountRepository userAccountRepository;

	@Mock
	private RedisCachingService redisCachingService;

	@Mock
	private MapperUtil mapperUtil;

	@Mock
	private FipClientService fipClientService;

	@Mock
	private CentralRegistryClientService centralRegistryClientService;

	@InjectMocks
	@Spy
	private static AccountManagementService accountManagementService;

	//
	private static UserAccount user;

	private static AccountDiscoveryAppRequestDTO accountDiscoveryAppRequest_hdfc;

	private static AccountDiscoveryAppRequestDTO accountDiscoveryAppRequest_icici;
	
	private static AccountDiscoveryAppResponseDTO accountDiscoveryAppResponse_idfc;
	private static AccountDiscoveryAppResponseDTO accountDiscoveryAppResponse_hdfc;
	private static AccountDiscoveryAppResponseDTO accountDiscoveryAppResponse_icici;

	private static List<RegistryEntity> registryEntity_fipList;

	private static AccountDiscoveryAppResponseDTO	 accountDiscoveryAppResponse;
	private static AccountDiscoveryAppRequestDTO     accountDiscoveryAppRequest;
	
	private static RegistryEntity					 registryEntity;
	private static FipDetailsForFipDTO				 fipDetailsForFip;	
	private static List<IdentifierSpecification>     identifierSpecificationList;
	private static AccountDiscoveryFipRequestDTO     accountDiscoveryFipRequest; 
	private static List<DiscoveredAccountForAppDTO>  discoveredAccountForAppList;
	private static AccountDiscoveryFipResponseDTO 	 accountDiscoveryFipResponse;

	private static AccountDiscoveryAppResponseDTO accountDiscoveryAppResponse_NoAccountsDiscovered;

	private static List<DiscoveredAccountForAppDTO> discoveredAccountForAppList_NoAccountsDiscovered;

	private static AccountDiscoveryFipResponseDTO accountDiscoveryFipResponse_NoAccountsDiscovered;

	private static AutoAccountDiscoveryResponseAppDTO autoAccountDiscoveryAppResponse;

	private static AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryAppRequest;

	private static AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequest;

	private static AccountDiscoveryPollingAppResponseDTO accountDiscoveryPollingAppResponse_AccountsPresent;

	private static AccountDiscoveryPollingAppResponseDTO accountDiscoveryPollingAppResponse_AccountsNotPresent;

	private static List<FinancialInformationProvider> fipList;
	
	private static List<FinancialInformationProvider> fipList_NoFip;
	
	private static List<FipForAppDTO> fipForAppList;

	private static List<FipForAppDTO> fipForAppList_NoFip;
	
	@BeforeClass
	public static void init() {
		prepareForDiscoverAccountsTest_AccountsDiscovered();
		prepareForDiscoverAccountsTest_NoAccountsDiscovered();
		prepareForAutoDiscoverAccountsTest_FipsAvailable();
		prepareForAutoDiscoverAccountsTest_FipsNotAvailable();
		prepareForPollCache_AccountsPresent();
		prepareForPollCache_AccountsNotPresent();
	}
	
	private static void prepareForDiscoverAccountsTest_AccountsDiscovered() {
		String accountDiscoveryAppResponseJson = FileUtil.fetchTestDataForClass("accountDiscoveryAppResponse.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryAppRequestJson = FileUtil.fetchTestDataForClass("accountDiscoveryAppRequest.json",
				AccountManagementServiceTest.class.getSimpleName());

		String registryEntityJson = FileUtil.fetchTestDataForClass("registryEntity.json",
				AccountManagementServiceTest.class.getSimpleName());

		String fipDetailsForFipJson = FileUtil.fetchTestDataForClass("fipDetailsForFip.json",
				AccountManagementServiceTest.class.getSimpleName());

		String identifierSpecificationListJson = FileUtil.fetchTestDataForClass("identifierSpecs.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryFipRequestJson = FileUtil.fetchTestDataForClass("accountDiscoveryFipRequest.json",
				AccountManagementServiceTest.class.getSimpleName());

		String discoveredAccountForAppListJson = FileUtil.fetchTestDataForClass("discoveredAccountForAppList.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryFipResponseJson = FileUtil.fetchTestDataForClass("accountDiscoveryFipResponse.json",
				AccountManagementServiceTest.class.getSimpleName());

		try {
			accountDiscoveryAppResponse = MAPPER.readValue(accountDiscoveryAppResponseJson,
					AccountDiscoveryAppResponseDTO.class);
			accountDiscoveryAppRequest = MAPPER.readValue(accountDiscoveryAppRequestJson,
					AccountDiscoveryAppRequestDTO.class);
			registryEntity = MAPPER.readValue(registryEntityJson, RegistryEntity.class);
			fipDetailsForFip = MAPPER.readValue(fipDetailsForFipJson, FipDetailsForFipDTO.class);
			identifierSpecificationList = MAPPER.readValue(identifierSpecificationListJson,
					new TypeReference<ArrayList<IdentifierSpecification>>() {
					});
			accountDiscoveryFipRequest = MAPPER.readValue(accountDiscoveryFipRequestJson,
					AccountDiscoveryFipRequestDTO.class);
			discoveredAccountForAppList = MAPPER.readValue(discoveredAccountForAppListJson,
					new TypeReference<ArrayList<DiscoveredAccountForAppDTO>>() {
					});
			accountDiscoveryFipResponse = MAPPER.readValue(accountDiscoveryFipResponseJson,
					AccountDiscoveryFipResponseDTO.class);

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
	

	private static void prepareForDiscoverAccountsTest_NoAccountsDiscovered() {
		String accountDiscoveryAppResponse_NoAccountsDiscoveredJson = FileUtil.fetchTestDataForClass("accountDiscoveryAppResponse_NoAccountsDiscovered.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryAppRequestJson = FileUtil.fetchTestDataForClass("accountDiscoveryAppRequest.json",
				AccountManagementServiceTest.class.getSimpleName());

		String registryEntityJson = FileUtil.fetchTestDataForClass("registryEntity.json",
				AccountManagementServiceTest.class.getSimpleName());

		String fipDetailsForFipJson = FileUtil.fetchTestDataForClass("fipDetailsForFip.json",
				AccountManagementServiceTest.class.getSimpleName());

		String identifierSpecificationListJson = FileUtil.fetchTestDataForClass("identifierSpecs.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryFipRequestJson = FileUtil.fetchTestDataForClass("accountDiscoveryFipRequest.json",
				AccountManagementServiceTest.class.getSimpleName());

		String discoveredAccountForAppList_NoAccountsDiscoveredJson = FileUtil.fetchTestDataForClass("discoveredAccountForAppList_NoAccountsDiscovered.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryFipResponse_NoAccountsDiscoveredJson = FileUtil.fetchTestDataForClass("accountDiscoveryFipResponse_NoAccountsDiscovered.json",
				AccountManagementServiceTest.class.getSimpleName());

		try {
			accountDiscoveryAppResponse_NoAccountsDiscovered = MAPPER.readValue(accountDiscoveryAppResponse_NoAccountsDiscoveredJson,
																AccountDiscoveryAppResponseDTO.class);
			accountDiscoveryAppRequest 	= MAPPER.readValue(accountDiscoveryAppRequestJson,
																AccountDiscoveryAppRequestDTO.class);
			registryEntity 				= MAPPER.readValue(registryEntityJson, RegistryEntity.class);
			fipDetailsForFip		 	= MAPPER.readValue(fipDetailsForFipJson, FipDetailsForFipDTO.class);
			identifierSpecificationList = MAPPER.readValue(identifierSpecificationListJson,
																new TypeReference<ArrayList<IdentifierSpecification>>() {});
			accountDiscoveryFipRequest 	= MAPPER.readValue(accountDiscoveryFipRequestJson,
																AccountDiscoveryFipRequestDTO.class);
			discoveredAccountForAppList_NoAccountsDiscovered = MAPPER.readValue(discoveredAccountForAppList_NoAccountsDiscoveredJson,
																new TypeReference<ArrayList<DiscoveredAccountForAppDTO>>() {});
			accountDiscoveryFipResponse_NoAccountsDiscovered = MAPPER.readValue(accountDiscoveryFipResponse_NoAccountsDiscoveredJson,
																AccountDiscoveryFipResponseDTO.class);

		} catch (JsonParseException e) {
			throw new RuntimeException("Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (IOException e) {
			throw new RuntimeException("Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		}
	}

	
	private static void prepareForAutoDiscoverAccountsTest_FipsAvailable() {

		String autoAccountDiscoveryAppResponseJson = FileUtil.fetchTestDataForClass("autoAccountDiscoveryAppResponse.json",
				AccountManagementServiceTest.class.getSimpleName());

		String autoAccountDiscoveryAppRequestJson = FileUtil.fetchTestDataForClass("autoAccountDiscoveryAppRequest.json",
				AccountManagementServiceTest.class.getSimpleName());
		
		String accountDiscoveryAppRequest_hdfcJson = FileUtil.fetchTestDataForClass("accountDiscoveryAppRequest_hdfc.json",
				AccountManagementServiceTest.class.getSimpleName());
		
		String accountDiscoveryAppRequest_iciciJson = FileUtil.fetchTestDataForClass("accountDiscoveryAppRequest_icici.json",
				AccountManagementServiceTest.class.getSimpleName());

		String userAccountJson = FileUtil.fetchTestDataForClass("userAccount.json",AccountManagementServiceTest.class.getSimpleName());
		
		String registryEntity_fipListJson = FileUtil.fetchTestDataForClass("registryEntity_fipList.json",AccountManagementServiceTest.class.getSimpleName());
		
		String accountDiscoveryAppResponse_iciciJson= FileUtil.fetchTestDataForClass("accountDiscoveryAppResponse_icici.json",
				AccountManagementServiceTest.class.getSimpleName());;
		
		String accountDiscoveryAppResponse_hdfcJson= FileUtil.fetchTestDataForClass("accountDiscoveryAppResponse_hdfc.json",
				AccountManagementServiceTest.class.getSimpleName());;
		
		try {
			autoAccountDiscoveryAppResponse = MAPPER.readValue(autoAccountDiscoveryAppResponseJson,
					AutoAccountDiscoveryResponseAppDTO.class);
			
			autoAccountDiscoveryAppRequest = MAPPER.readValue(autoAccountDiscoveryAppRequestJson,
					AutoAccountDiscoveryRequestAppDTO.class);
			
			user = MAPPER.readValue(userAccountJson,UserAccount.class);
			
			registryEntity_fipList = MAPPER.readValue(registryEntity_fipListJson,
					new TypeReference<ArrayList<RegistryEntity>>() {
			});
			
			accountDiscoveryAppRequest_hdfc  = MAPPER.readValue(accountDiscoveryAppRequest_hdfcJson,
					AccountDiscoveryAppRequestDTO.class);
			
			accountDiscoveryAppResponse_hdfc = MAPPER.readValue(accountDiscoveryAppResponse_hdfcJson,
					AccountDiscoveryAppResponseDTO.class);
			
			accountDiscoveryAppRequest_icici  = MAPPER.readValue(accountDiscoveryAppRequest_iciciJson,
					AccountDiscoveryAppRequestDTO.class);
			
			accountDiscoveryAppResponse_icici = MAPPER.readValue(accountDiscoveryAppResponse_iciciJson,
					AccountDiscoveryAppResponseDTO.class);

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
	
	private static void prepareForAutoDiscoverAccountsTest_FipsNotAvailable() {
		String autoAccountDiscoveryAppRequestJson = FileUtil.fetchTestDataForClass(
				"autoAccountDiscoveryAppRequest.json", AccountManagementServiceTest.class.getSimpleName());
		
		try {
			autoAccountDiscoveryAppRequest = MAPPER.readValue(autoAccountDiscoveryAppRequestJson,
					AutoAccountDiscoveryRequestAppDTO.class);			
		} catch (JsonParseException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (JsonMappingException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		} catch (IOException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed while deserializaing from json to pojo "
					+ "before testing discoverAccounts() unit of AccountManagementService", e);
		}}

	private static void prepareForPollCache_AccountsPresent() {
		String accountDiscoveryPollingAppResponse_AccountsPresentJson = FileUtil.fetchTestDataForClass(
				"accountDiscoveryPollingAppResponse_AccountsPresent.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryPollingAppRequestJson = FileUtil.fetchTestDataForClass(
				"accountDiscoveryPollingAppRequest.json", AccountManagementServiceTest.class.getSimpleName());

		String fipListJson = FileUtil.fetchTestDataForClass("fipList.json",AccountManagementServiceTest.class.getSimpleName());

		String fipForAppListJson = FileUtil.fetchTestDataForClass("fipForAppList.json",AccountManagementServiceTest.class.getSimpleName());
		
		try {
			accountDiscoveryPollingAppResponse_AccountsPresent = MAPPER.readValue(
					accountDiscoveryPollingAppResponse_AccountsPresentJson,
					AccountDiscoveryPollingAppResponseDTO.class);

			accountDiscoveryPollingAppRequest = MAPPER.readValue(accountDiscoveryPollingAppRequestJson,
					AccountDiscoveryPollingAppRequestDTO.class);

			fipList = MAPPER.readValue(fipListJson, new TypeReference<ArrayList<FinancialInformationProvider>>() {
			});
			
			fipForAppList = MAPPER.readValue(fipForAppListJson, new TypeReference<ArrayList<FipForAppDTO>>() {
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
	
	private static void prepareForPollCache_AccountsNotPresent() {
		
		String accountDiscoveryPollingAppResponse_AccountsNotPresentJson = FileUtil.fetchTestDataForClass("accountDiscoveryPollingAppResponse_AccountsNotPresent.json",
				AccountManagementServiceTest.class.getSimpleName());

		String accountDiscoveryPollingAppRequestJson = FileUtil.fetchTestDataForClass("accountDiscoveryPollingAppRequest.json",
				AccountManagementServiceTest.class.getSimpleName());
		
		String fipList_NoFipJson = FileUtil.fetchTestDataForClass("fipList_NoFip.json",
				AccountManagementServiceTest.class.getSimpleName());
		
		String fipForAppList_NoFipJson = FileUtil.fetchTestDataForClass("fipForAppList_NoFip.json",AccountManagementServiceTest.class.getSimpleName());
		
		try {
			accountDiscoveryPollingAppResponse_AccountsNotPresent = MAPPER.readValue(accountDiscoveryPollingAppResponse_AccountsNotPresentJson,
					AccountDiscoveryPollingAppResponseDTO.class);

			accountDiscoveryPollingAppRequest = MAPPER.readValue(accountDiscoveryPollingAppRequestJson,AccountDiscoveryPollingAppRequestDTO.class);

			fipList_NoFip = MAPPER.readValue(fipList_NoFipJson, new TypeReference<ArrayList<FinancialInformationProvider>>() {
			});

			fipForAppList_NoFip = MAPPER.readValue(fipForAppList_NoFipJson, new TypeReference<ArrayList<FipForAppDTO>>() {
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
	
	@AfterClass
	public static void cleanUp() {
		accountDiscoveryAppResponse = null;
		accountDiscoveryAppRequest  = null;
		registryEntity				= null;
		fipDetailsForFip			= null;
		identifierSpecificationList = null;
    	accountDiscoveryFipRequest	= null;
  		discoveredAccountForAppList = null;	     	
 		accountDiscoveryFipResponse = null;
	}
	
	// AccountDiscoveryAppResponseDTO
	// AccountDiscoveryAppRequestDTO
	// RegistryEntity
	// FipDetailsForFipDTO
	// List<IdentifierSpecification>
	// AccountDiscoveryFipRequestDTO
	// List<DiscoveredAccountForAppDTO>
	// AccountDiscoveryFipResponse
	
	@Test
	public void discoverAccountsTest_AccountsDiscovered() {
		AccountDiscoveryAppResponseDTO expected = accountDiscoveryAppResponse;

		when(registryService.getFip(accountDiscoveryAppRequest.getFipId())).thenReturn(registryEntity);

		when(mapperUtil.mapFipEntityToFipDetails(registryEntity)).thenReturn(fipDetailsForFip);

		when(identifierSpecificationRepository.findAll()).thenReturn(identifierSpecificationList);

		when(mapperUtil.mapAccountDiscoveryAppRequestToAccountDiscoveryFipRequest(accountDiscoveryAppRequest,
				fipDetailsForFip, identifierSpecificationList)).thenReturn(accountDiscoveryFipRequest);

		when(fipClientService.discoverAccounts(accountDiscoveryFipRequest, registryEntity))
				.thenReturn(accountDiscoveryFipResponse);

		when(mapperUtil.mapDiscoverdAccountsForFipToDiscoveredAccountForApp(
				accountDiscoveryFipResponse.getDiscoveredAccounts())).thenReturn(discoveredAccountForAppList);

		AccountDiscoveryAppResponseDTO actual = accountManagementService
				.discoverAccounts(accountDiscoveryAppRequest);
		
		assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(expected);
	}

	// AccountDiscoveryAppRequestDTO
	// RegistryEntity
	// FipDetailsForFipDTO
	// List<IdentifierSpecification>
	// AccountDiscoveryFipRequestDTO
	// List<DiscoveredAccountForAppDTO>
	// AccountDiscoveryFipResponse
	
	/*
	 * TODO: Test case needs to be updated to expect a Resource Not Found Custom Exception.
	 * Ideally discoverAccounts() should throw a Resource Not Found(404) exception if no accounts are discovered.
	 * 
	 * However, currently this method returns a empty list of accounts. 
	 */
	
	@Test
	public void discoverAccountsTest_NoAccountsDiscovered() {
		AccountDiscoveryAppResponseDTO expected = accountDiscoveryAppResponse_NoAccountsDiscovered;

		when(registryService.getFip(accountDiscoveryAppRequest.getFipId())).thenReturn(registryEntity);

		when(mapperUtil.mapFipEntityToFipDetails(registryEntity)).thenReturn(fipDetailsForFip);

		when(identifierSpecificationRepository.findAll()).thenReturn(identifierSpecificationList);

		when(mapperUtil.mapAccountDiscoveryAppRequestToAccountDiscoveryFipRequest(accountDiscoveryAppRequest,
				fipDetailsForFip, identifierSpecificationList)).thenReturn(accountDiscoveryFipRequest);

		when(fipClientService.discoverAccounts(accountDiscoveryFipRequest, registryEntity))
				.thenReturn(accountDiscoveryFipResponse_NoAccountsDiscovered);


		List<DiscoveredAccountFipDTO> discoveredAccountFipList = accountDiscoveryFipResponse.getDiscoveredAccounts();
		
		when(mapperUtil.mapDiscoverdAccountsForFipToDiscoveredAccountForApp(discoveredAccountFipList))
												.thenReturn(discoveredAccountForAppList_NoAccountsDiscovered);

		AccountDiscoveryAppResponseDTO actual = accountManagementService
				.discoverAccounts(accountDiscoveryAppRequest);
		
		assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(expected);
	}
	
//	@Test(expected = CustomException.class)
//	public void discoverAccountsTest_InvalidIdentifier() {
//
//	}

	@Test
	public void autoDiscoverAccountsTest_FipsAvailable() {
		AutoAccountDiscoveryResponseAppDTO expected = autoAccountDiscoveryAppResponse;
		
		List<RegistryEntity> fipList = registryEntity_fipList;

		Mockito.doReturn(fipList).when(registryService).getFips();
		
		UserAccount userAccount = user;

		Mockito.doReturn(userAccount)
			.when(userAccountRepository).findByCustomerId(autoAccountDiscoveryAppRequest.getCustomerId());

		Mockito.doReturn(accountDiscoveryAppRequest_hdfc)
			.when(mapperUtil).mapAutoToManualAccountDiscoveryAppRequest(autoAccountDiscoveryAppRequest, 
					fipList.get(0), userAccount);

		Mockito.doReturn(accountDiscoveryAppRequest_icici)
			.when(mapperUtil).mapAutoToManualAccountDiscoveryAppRequest(autoAccountDiscoveryAppRequest, 
					fipList.get(1),userAccount);

		Mockito.doReturn(accountDiscoveryAppResponse_hdfc).when(accountManagementService)
				.discoverAccounts(accountDiscoveryAppRequest_hdfc);
		
		Mockito.doReturn(accountDiscoveryAppResponse_icici).when(accountManagementService)
				.discoverAccounts(accountDiscoveryAppRequest_icici);

		Integer numberOfRecordsPublished = 2;

		Mockito.doReturn(registryEntity_fipList).when(registryService).getFips();

		Mockito.doReturn(numberOfRecordsPublished).when(redisCachingService)
		.publishAccountDiscoveryResponse(accountDiscoveryAppResponse_idfc);
		
		Mockito.doReturn(numberOfRecordsPublished).when(redisCachingService)
		.publishAccountDiscoveryResponse(accountDiscoveryAppResponse_hdfc);

		AutoAccountDiscoveryResponseAppDTO actual = accountManagementService
				.autoDiscoverAccounts(autoAccountDiscoveryAppRequest);
		
		assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(expected);
	}

	//AutoAccountDiscoveryResponseAppDTO
	//AutoAccountDiscoveryRequestAppDTO
	//List<FIPRegistryAppResponseDTO>
	//UserAccount
	//AccountDiscoveryAppRequestDTO
	//AccountDiscoveryAppResponseDTO
	//Integer numberOfRecordsPublished
	
	@Test(expected = CustomException.class)
	public void autoDiscoverAccountsTest_FipsNotAvailable() {
		// These are the FIPs from the registry
		Mockito.doReturn(null).when(registryService).getFips();

		accountManagementService
				.autoDiscoverAccounts(autoAccountDiscoveryAppRequest);
	}

//	AccountDiscoveryPollingAppResponseDTO
//	accountDiscoveryPollingAppRequestDTO
//	List<FinancialInformationProvider>
//	Integer numberOfSearchedFips
//  Integer numberOfRecordsDeleted	
	
	@Test
	public void pollCache_AccountsPresent() {
		
		AccountDiscoveryPollingAppResponseDTO expected = accountDiscoveryPollingAppResponse_AccountsPresent;
		
		List<FinancialInformationProvider> fips = fipList;

		Mockito.doReturn(fips)
					.when(redisCachingService).fetchAccountDiscoveryResponse(accountDiscoveryPollingAppRequest);
		
		List<FipForAppDTO> fipsForApp = fipForAppList;
		
		Mockito.doReturn(fipsForApp).when(mapperUtil).mapFinanciaInformationProviderListToFipForAppList(fips);
		
		Integer numberOfSearchedFips = 2;
		
		Mockito.doReturn(numberOfSearchedFips)
		.when(redisCachingService).fetchCountOfSearchedFips(accountDiscoveryPollingAppRequest);
		
		Integer numberOfRecordsDeleted = 2;
		
		Mockito.doReturn(numberOfRecordsDeleted)
				.when(redisCachingService).deleteAccountDiscoveryResponse(accountDiscoveryPollingAppRequest, fips);

		AccountDiscoveryPollingAppResponseDTO actual = accountManagementService.pollCache(accountDiscoveryPollingAppRequest);
		
		assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(expected);
	}

	//AccountDiscoveryPollingAppResponseDTO
	//accountDiscoveryPollingAppRequestDTO
	//List<FinancialInformationProvider>
	//Integer numberOfSearchedFips
    //Integer numberOfRecordsDeleted
	
	@Test
	public void pollCache_AccountsNotPresent() {
		
		AccountDiscoveryPollingAppResponseDTO expected = accountDiscoveryPollingAppResponse_AccountsNotPresent;
		
		List<FinancialInformationProvider> fips = fipList_NoFip;

		Mockito.doReturn(fips)
					.when(redisCachingService).fetchAccountDiscoveryResponse(accountDiscoveryPollingAppRequest);
		
		List<FipForAppDTO> fipsForApp = fipForAppList_NoFip;
		
		Mockito.doReturn(fipsForApp).when(mapperUtil).mapFinanciaInformationProviderListToFipForAppList(fips);
		
		Integer numberOfSearchedFips = 2;
		
		Mockito.doReturn(numberOfSearchedFips)
		.when(redisCachingService).fetchCountOfSearchedFips(accountDiscoveryPollingAppRequest);
		
		Integer numberOfRecordsDeleted = 2;
		
		Mockito.doReturn(numberOfRecordsDeleted)
				.when(redisCachingService).deleteAccountDiscoveryResponse(accountDiscoveryPollingAppRequest, fips);

		AccountDiscoveryPollingAppResponseDTO actual = accountManagementService
														.pollCache(accountDiscoveryPollingAppRequest);
		
		assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(expected);
	}
}
