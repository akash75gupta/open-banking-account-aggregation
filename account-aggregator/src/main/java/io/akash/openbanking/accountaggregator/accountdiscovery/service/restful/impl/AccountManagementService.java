package io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.DateUtil;
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
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.api.IAccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/**
 * This is an implementation of the Account Management Service. 
 * It caters to all the services pertaining to Account Management.
 * 
 * @author 
 * @author Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since 2019-03-27
 **/

@Service
public class AccountManagementService implements IAccountManagementService {

	private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

	@Autowired
	IdentifierSpecificationRepository identifierSpecificationRepository;

	@Autowired
	RegistryService registryService;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private RedisCachingService redisCachingService;

	@Autowired
	MapperUtil mapperUtil;

	@Autowired
	FipClientService fipClientService;

	@Autowired
	CentralRegistryClientService centralRegistryClientService;

	@Override
	public AccountDiscoveryAppResponseDTO discoverAccounts(
				AccountDiscoveryAppRequestDTO accountDiscoveryAppRequestDTO){
		
		log.info("Executing AccountManagementService.discoverAccounts() with Param - "
				+"AccountDiscoveryAppResponseDTO: {} "
				+"Sending request for discovering all the accounts for a given customer",accountDiscoveryAppRequestDTO);

		RegistryEntity fip = registryService.getFip(accountDiscoveryAppRequestDTO.getFipId());

		if(null == fip) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Fip could not be found for the given accountDiscoveryAppRequestDTO.getFipId()",
					new NullPointerException());
		}
		
		FipDetailsForFipDTO fipDetailsDTO = mapperUtil.mapFipEntityToFipDetails(fip);
		
		List<IdentifierSpecification> identifierSpecs = null;
		try {
			identifierSpecs = identifierSpecificationRepository.findAll();
		}catch (Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Exception occured while fetching identifier specifications from DB.")
					.append("Unrecognized exception! Need to handle.")
					.append(System.getProperty("line.separator"))
					.append("RCA: Unknown");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					expMessageBuilder.toString(),e);
		}
		
		if(null == identifierSpecs) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Identifier specifications not found!")
					.append(System.getProperty("line.separator"))
					.append("RCA: Identifier specifications not present in DB.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,expMessageBuilder.toString());
		}
		
		AccountDiscoveryFipRequestDTO accDiscoveryDTO = 
				mapperUtil.mapAccountDiscoveryAppRequestToAccountDiscoveryFipRequest(
										accountDiscoveryAppRequestDTO, fipDetailsDTO,identifierSpecs);

		AccountDiscoveryFipResponseDTO accountDiscoveryFipResponseDTO 
							= fipClientService.discoverAccounts(accDiscoveryDTO, fip);

		List<DiscoveredAccountFipDTO> discoveredAccounts = accountDiscoveryFipResponseDTO.getDiscoveredAccounts();
		
		List<DiscoveredAccountForAppDTO> mappedAccounts 
							= mapperUtil.mapDiscoverdAccountsForFipToDiscoveredAccountForApp(discoveredAccounts);

		AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO 
										= prepareAccountDiscoveryAppResponse(accountDiscoveryAppRequestDTO,mappedAccounts,fip);
		
		log.info("Returning from AccountManagementService.discoverAccounts():"
				+"Value - accountDiscoveryAppResponseDTO: {}",accountDiscoveryAppResponseDTO);
		
		return accountDiscoveryAppResponseDTO;
	}

	private AccountDiscoveryAppResponseDTO prepareAccountDiscoveryAppResponse(
			AccountDiscoveryAppRequestDTO accountDiscoveryAppRequestDTO, List<DiscoveredAccountForAppDTO> mappedAccounts,
			RegistryEntity fipEntity) {
		
		log.info("Executing AccountManagementService.prepareAccountDiscoveryAppResponse():"
				+ "Preparing Account Discovery App Response - Param 1:mappedAccounts {} and Param 2:{}",mappedAccounts,fipEntity);
		
		AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO = new AccountDiscoveryAppResponseDTO();
		
		accountDiscoveryAppResponseDTO.setTransactionId(accountDiscoveryAppRequestDTO.getTransactionId());
		accountDiscoveryAppResponseDTO.setCustomerId(accountDiscoveryAppRequestDTO.getCustomerId());
		accountDiscoveryAppResponseDTO.setVer(accountDiscoveryAppRequestDTO.getVer());
		accountDiscoveryAppResponseDTO.setTimestamp(DateUtil.getCurrentTimeStamp());
		
		FipForAppDTO fipAccountDiscoveryAppResponseDTO = new FipForAppDTO();
		
		fipAccountDiscoveryAppResponseDTO.setId(fipEntity.getBusinessId());
		fipAccountDiscoveryAppResponseDTO.setName(fipEntity.getName());
		fipAccountDiscoveryAppResponseDTO.setLogoUrl(fipEntity.getLogoUrl());
		fipAccountDiscoveryAppResponseDTO.setDiscoveredAccounts(mappedAccounts);
		
		List<FipForAppDTO> fipForAppList = new ArrayList<>();
		fipForAppList.add(fipAccountDiscoveryAppResponseDTO);
		
		accountDiscoveryAppResponseDTO.setFipForAppList(fipForAppList);
		
		log.info("Returning from AccountManagementService.prepareAccountDiscoveryAppResponse():"
				+ "Value - responseToSendForDiscoverDTO: {}",accountDiscoveryAppResponseDTO);
		
		return accountDiscoveryAppResponseDTO;
	}

	@Override
	public AutoAccountDiscoveryResponseAppDTO autoDiscoverAccounts(
			AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryRequest) {
		
		log.info("Executing AccountManagementService.autoDiscoverAccounts():"
				+ "Fetching accounts asynchronously in a non-blocking manner with"
				+ "Param 1:autoAccountDiscoveryRequest {}",autoAccountDiscoveryRequest);
		
		Long startTime = System.currentTimeMillis();
		
		AutoAccountDiscoveryResponseAppDTO autoAccountDiscoveryResponseAppDTO = null;

		// These are the FIPs from the registry
		List<RegistryEntity> fips = registryService.getFips();
		
		
		if(fips == null) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("No record found!")
					.append(System.getProperty("line.separator"))
					.append("RCA: There are no FIPs in the DB.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,expMessageBuilder.toString());
		}
		
		for (RegistryEntity fip : fips) {
			// find the users by VUA, here customerId is the VUA
			UserAccount userAccount = userAccountRepository.findByCustomerId(autoAccountDiscoveryRequest.getCustomerId());
			
			if(userAccount == null) {
				StringBuilder expMessageBuilder = new StringBuilder()
						.append("Invalid customer ID!")
						.append(System.getProperty("line.separator"))
						.append("RCA: No such customer exists. The customer ID provided in the request body is incorrect.");
				throw new CustomException(HttpStatus.BAD_REQUEST,expMessageBuilder.toString());
			}
			
			AccountDiscoveryAppRequestDTO accountDiscoveryAppRequestDTO = 
					mapperUtil.mapAutoToManualAccountDiscoveryAppRequest(autoAccountDiscoveryRequest, fip, userAccount);

			log.debug("After Mapping from auto to manual dicovery request dto");
			log.debug("{}", accountDiscoveryAppRequestDTO);

			CompletableFuture<AccountDiscoveryAppResponseDTO> accountDiscoveryAppResponseDTOFuture 
				= CompletableFuture.supplyAsync(() -> {
					AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO = null;
					accountDiscoveryAppResponseDTO = discoverAccounts(accountDiscoveryAppRequestDTO);
			
					return accountDiscoveryAppResponseDTO;
				});
						
			CompletableFuture<Integer> noOfAccountsPublished 
								= accountDiscoveryAppResponseDTOFuture.thenApply(accountDiscoveryAppResponseDTO -> {
				return redisCachingService.publishAccountDiscoveryResponse(accountDiscoveryAppResponseDTO);
			});
			
			log.info("Number of accounts for FIP {}: {}",fip.getName(),noOfAccountsPublished);
		}
		
		autoAccountDiscoveryResponseAppDTO = new AutoAccountDiscoveryResponseAppDTO();
		
		autoAccountDiscoveryResponseAppDTO.setFipCount(fips.size());
		autoAccountDiscoveryResponseAppDTO.setTransactionId(autoAccountDiscoveryRequest.getTransactionId());
		autoAccountDiscoveryResponseAppDTO.setCustomerId(autoAccountDiscoveryRequest.getCustomerId());
		autoAccountDiscoveryResponseAppDTO.setVer(autoAccountDiscoveryRequest.getVer());
		autoAccountDiscoveryResponseAppDTO.setTimestamp(DateUtil.getCurrentTimeStamp());

		log.info("Returning from AccountManagementService.autoDiscoverAccounts() with"
				+ "value - autoAccountDiscoveryResponseAppDTO(AutoAccountDiscoveryResponseAppDTO): {}",autoAccountDiscoveryResponseAppDTO);

		Long endTime = System.currentTimeMillis();
		log.info("Execution time for AccountManagementService.getAccountsAsync(): {}", (startTime - endTime) / 1000);
		
		return autoAccountDiscoveryResponseAppDTO;
	}

	@Override
	public AccountDiscoveryPollingAppResponseDTO pollCache(
			AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO) {

		log.info("Executing AccountManagementService.pollCache() with Param - accountDiscoveryPollingAppRequestDTO: {}"
				+"Polling the cache to retrieving all the fips along with their discovered accounts from cache", 
				accountDiscoveryPollingAppRequestDTO);

		AccountDiscoveryPollingAppResponseDTO accountDiscoveryPollingAppResponseDTO = null;

		List<FinancialInformationProvider> fips = 
					redisCachingService.fetchAccountDiscoveryResponse(accountDiscoveryPollingAppRequestDTO);
		
		Integer numberOfSearchedFips = redisCachingService.fetchCountOfSearchedFips(accountDiscoveryPollingAppRequestDTO);
				
		accountDiscoveryPollingAppResponseDTO = new AccountDiscoveryPollingAppResponseDTO();
		
		accountDiscoveryPollingAppResponseDTO.setCustomerId(accountDiscoveryPollingAppRequestDTO.getCustomerId());
		accountDiscoveryPollingAppResponseDTO.setTransactionId(accountDiscoveryPollingAppRequestDTO.getTransactionId());
		accountDiscoveryPollingAppResponseDTO.setTimestamp(accountDiscoveryPollingAppRequestDTO.getTimestamp());
		accountDiscoveryPollingAppResponseDTO.setVer(accountDiscoveryPollingAppRequestDTO.getVer());
		accountDiscoveryPollingAppResponseDTO.setNumberOfSearchedFips(numberOfSearchedFips);
		accountDiscoveryPollingAppResponseDTO.setFipForAppList(mapperUtil.mapFinanciaInformationProviderListToFipForAppList(fips));
		
		redisCachingService.deleteAccountDiscoveryResponse(accountDiscoveryPollingAppRequestDTO, fips);
		
		return accountDiscoveryPollingAppResponseDTO;
	}
}
