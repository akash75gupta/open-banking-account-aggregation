package io.akash.openbanking.accountaggregator.accountdiscovery.helper.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.GeneralConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppRequestIdentifierDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipRequestCustomerDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipRequestIdentifierDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryRequestAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.DiscoveredAccountFipDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.DiscoveredAccountForAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FIPRegistryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FipDetailsForFipDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FipForAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierCategoryEnum;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierTypeEnum;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.AccountDiscoveryCacheRecord;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialAccount;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.Identifier;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.IdentifierSpecification;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.UserAccount;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;

/**
 * Purpose:- 
 * This is a general purpose class used for mapping one type of object to another type of object 
 * as per business use cases.
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02 
 * 
 **/

@Component
public class MapperUtil {
	
	private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

	@Autowired
	Environment env;
	
	public AccountDiscoveryAppRequestDTO mapAutoToManualAccountDiscoveryAppRequest(
			AutoAccountDiscoveryRequestAppDTO request, RegistryEntity fip, UserAccount user) {
			
			log.info("Executing MapperUtil.mapAutoToManualAccountDiscoveryAppRequest() with Param 1 - AutoAccountDiscoveryRequestAppDTO: {}"
					+"Param 2 - FIPRegistryAppResponseDTO: {} and Param 3 - UserAccount: {}"
					+"Getting identifer category from identifier specification",request,fip,user);
		
			AccountDiscoveryAppRequestDTO accountDiscoveryRequestAppDTO = new AccountDiscoveryAppRequestDTO();
			
			accountDiscoveryRequestAppDTO.setTransactionId(request.getTransactionId());
			accountDiscoveryRequestAppDTO.setCustomerId(request.getCustomerId());
			accountDiscoveryRequestAppDTO.setFipId(fip.getBusinessId());
			accountDiscoveryRequestAppDTO.setMeta(request.getMeta());
			accountDiscoveryRequestAppDTO.setIdentifiers(
					mapUserAccountIdentifiersToIdentifiersInAccountDiscoveryAppRequestDTO(user));
			
		    log.info("Returning from MapperUtil.mapAutoToManualAccountDiscoveryAppRequest() with "
					+ "Value - AccountDiscoveryAppRequestDTO : {}",accountDiscoveryRequestAppDTO);
		    
			return accountDiscoveryRequestAppDTO;
	}

	/**
	 *
	 * @param  categoryMappingOfIdentifiersList List of CategoryMappingOfIdentifiers fetched from database
	 * @return map of IdentifierType as a key and IdentifierCategory as a value
	 * 
	 **/
	
	public Map<IdentifierTypeEnum, IdentifierCategoryEnum> 
						getIdentifierCategoriesFromSpecs(List<IdentifierSpecification> categoryMappingOfIdentifiersList){
		log.info("Executing MapperUtil.getIdentifierCategoriesFromSpecs() with Param - List<IdentifierSpecification>: {}"
				+"Getting identifer category from identifier specification",categoryMappingOfIdentifiersList);
	
	    Map<IdentifierTypeEnum, IdentifierCategoryEnum> mapOfCategory=new HashMap<>();
	       for(IdentifierSpecification obj:categoryMappingOfIdentifiersList)
	       {
	           mapOfCategory.put(obj.getType(),obj.getCategory());
	       }
	       
	       log.info("Returning from MapperUtil.getIdentifierCategoriesFromSpecs() with "
					+ "Value - Map<IdentifierTypeEnum, IdentifierCategoryEnum> : {}",mapOfCategory);
	       
	       return mapOfCategory;
	}

	public List<FIPRegistryAppResponseDTO> mapRegistryEntityListToFipForAppList(List<RegistryEntity> registryEntities){
		
		log.info("Executing MapperUtil.mapRegistryEntitiesToFIPRegistryAppResponse() with Param - registryEntities: {}"
				+ "Mapping entities from the registry to a dto that will be sent to the frontend app",registryEntities);

		 List<RegistryEntity> fips
				 = registryEntities.stream().filter(s -> 
	     									s.getType().equals(GeneralConstants.TYPE_FIP)).collect(Collectors.toList());
	     
	     List<FIPRegistryAppResponseDTO> fipRegistryAppResponseDtoList=new ArrayList<>();
	     for(RegistryEntity fip:fips)
	     {
	         FIPRegistryAppResponseDTO fipRegistryAppResponseDTO=new FIPRegistryAppResponseDTO();
	         fipRegistryAppResponseDTO.setId(fip.getBusinessId());
	         fipRegistryAppResponseDTO.setName(fip.getName());
	         fipRegistryAppResponseDTO.setIdentifiers(fip.getIdentifiers());
	         fipRegistryAppResponseDTO.setLogoUrl(fip.getLogoUrl());
	         fipRegistryAppResponseDtoList.add(fipRegistryAppResponseDTO);
	     }
	     
	     log.info("Returning from MapperUtil.mapRegistryEntitiesToFIPRegistryAppResponse() with "
					+ "Value - List<FIPRegistryAppResponseDTO>: {}",fipRegistryAppResponseDtoList);
	     
	     return fipRegistryAppResponseDtoList;
	 }

	public List<DiscoveredAccountForAppDTO> mapDiscoverdAccountsForFipToDiscoveredAccountForApp(
			List<DiscoveredAccountFipDTO> discoveredAccountFipDtoList) {
		log.info("Executing MapperUtil.mapDiscoverdAccountsForFipToDiscoveredAccountForApp() with Param1 - List<DiscoveredAccountFipDTO>: {}"
				+"- Mapping user attributes to identifiers that will be used in account discovery.",discoveredAccountFipDtoList);

		List<DiscoveredAccountForAppDTO> discoveredAccountAppDtoList = new ArrayList<DiscoveredAccountForAppDTO>();
		
		for (DiscoveredAccountFipDTO accountDTO : discoveredAccountFipDtoList) {
			DiscoveredAccountForAppDTO dicoveredAccount = new DiscoveredAccountForAppDTO();
			dicoveredAccount.setAccountNumber(accountDTO.getMaskedAccNumber());
			dicoveredAccount.setAccountType(accountDTO.getAccType());
			dicoveredAccount.setLinked(Boolean.FALSE);
			discoveredAccountAppDtoList.add(dicoveredAccount);
		}
		
		log.info("Returning from MapperUtil.mapDiscoverdAccountsForFipToDiscoveredAccountForApp() with value "
				+ "- List<DiscoveredAccountForAppDTO> : {}",discoveredAccountAppDtoList);
		
		return discoveredAccountAppDtoList;
	}

	public List<AccountDiscoveryAppRequestIdentifierDTO> mapUserAccountIdentifiersToIdentifiersInAccountDiscoveryAppRequestDTO(
																									UserAccount userAccount) {
		log.info("Executing MapperUtil.mapFipEntityToFipDetails() with Param1 - UserAccount: {}"
				+"- Mapping user attributes to identifiers that will be used in account discovery.",userAccount);
		
		List<AccountDiscoveryAppRequestIdentifierDTO> accountDiscoveryIdentifiers = new ArrayList<>();
		
		for(Identifier identifier:userAccount.getIdentifiers()) {
			AccountDiscoveryAppRequestIdentifierDTO accountDiscoveryIdentifier = new AccountDiscoveryAppRequestIdentifierDTO();
			
			accountDiscoveryIdentifier.setType(identifier.getType());
			accountDiscoveryIdentifier.setValue(identifier.getValue());
			accountDiscoveryIdentifiers.add(accountDiscoveryIdentifier);
		}
		
		log.info("Returning from MapperUtil.mapFipEntityToFipDetails() with value "
				+ "- List<AccountDiscoveryAppRequestIdentifierDTO> : {}",accountDiscoveryIdentifiers);
		
		return accountDiscoveryIdentifiers;
	}
	
	public AccountDiscoveryFipRequestDTO mapAccountDiscoveryAppRequestToAccountDiscoveryFipRequest
						(AccountDiscoveryAppRequestDTO accountDiscoveryAppRequest, 
								FipDetailsForFipDTO fipDetails, List<IdentifierSpecification> spec) {
	    
		log.info("Executing MapperUtil.mapAccountDiscoveryAppRequestToAccountDiscoveryFipRequest() with Param1 - AccountDiscoveryAppRequestDTO: {}"
				+", Param 2 - FipDetailsForFipDTO: {} and Param 3 - List<IdentifierSpecification> :{}"
				+"- Mapping FIP Entity to Fip Details DTO.",accountDiscoveryAppRequest,fipDetails,spec);
		
	    AccountDiscoveryFipRequestDTO accDiscoveryDTO = new AccountDiscoveryFipRequestDTO();
		
		accDiscoveryDTO.setVer(env.getProperty("ver"));
        accDiscoveryDTO.setTimestamp(DateUtil.getCurrentTimeStamp());
        accDiscoveryDTO.setTxnid(accountDiscoveryAppRequest.getTransactionId());
        
        AccountDiscoveryFipRequestCustomerDTO customerDTO=new AccountDiscoveryFipRequestCustomerDTO();
        customerDTO.setId(accountDiscoveryAppRequest.getCustomerId());
        accDiscoveryDTO.setCustomer(customerDTO);
        
        List<AccountDiscoveryAppRequestIdentifierDTO> accountDiscoveryAppRequestIdentifiers 
        												= accountDiscoveryAppRequest.getIdentifiers();
        
        List<AccountDiscoveryFipRequestIdentifierDTO> accountDiscoveryFipRequestIdentifiers =
        						mapAccountDiscoveryAppRequestIdentifiersToAccountDiscoveryFipRequestIdentifiers(
        											accountDiscoveryAppRequestIdentifiers,spec);
        
        accDiscoveryDTO.setIdentifiers(accountDiscoveryFipRequestIdentifiers);
        accDiscoveryDTO.setMeta(accountDiscoveryAppRequest.getMeta());

        accDiscoveryDTO.setFipDetails(fipDetails);
        
		log.info("Returning from MapperUtil.mapAccountDiscoveryAppRequestToAccountDiscoveryFipRequest() with value "
				+ "- AccountDiscoveryFipRequestDTO : {}",accDiscoveryDTO);
		
		return accDiscoveryDTO;
	}
	
	/**
    *
    * @param identifierReceviedFromClientDTOList
    * @return List of IdentifierDTO which is mapped on the basis of type that we received from AA client. 
    * So if we have type MOBILE then category will STRONG
    * 
    **/
   public List<AccountDiscoveryFipRequestIdentifierDTO> 
   					mapAccountDiscoveryAppRequestIdentifiersToAccountDiscoveryFipRequestIdentifiers(
   					List<AccountDiscoveryAppRequestIdentifierDTO> accountDiscoveryAppRequestIdentifiers, 
   					List<IdentifierSpecification> identifierSpecifications) {
		log.info("Executing MapperUtil.mapFipEntityToFipDetails() with Param1 - List<AccountDiscoveryAppRequestIdentifierDTO>: {}"
				+"and Param 2 - List<IdentifierSpecification>: {}"
				+"- Mapping AccountDiscoveryAppRequest To AccountDiscoveryFipRequest.",accountDiscoveryAppRequestIdentifiers,identifierSpecifications);

		Map<IdentifierTypeEnum, IdentifierCategoryEnum> identifierCategoriesMap = getIdentifierCategoriesFromSpecs(identifierSpecifications);

		List<AccountDiscoveryFipRequestIdentifierDTO> identifierDTOList = new ArrayList<AccountDiscoveryFipRequestIdentifierDTO>();

		for (AccountDiscoveryAppRequestIdentifierDTO accountDiscoveryAppRequestIdentifier : accountDiscoveryAppRequestIdentifiers) {
			AccountDiscoveryFipRequestIdentifierDTO identifierDTO = new AccountDiscoveryFipRequestIdentifierDTO();

			if (identifierCategoriesMap.containsKey(accountDiscoveryAppRequestIdentifier.getType())) {
				identifierDTO.setCategory(identifierCategoriesMap.get(accountDiscoveryAppRequestIdentifier.getType()));
				identifierDTO.setType(accountDiscoveryAppRequestIdentifier.getType());
				identifierDTO.setValue(accountDiscoveryAppRequestIdentifier.getValue());
			}

			identifierDTOList.add(identifierDTO);
		}
		
		log.info("Returning from MapperUtil.mapFipEntityToFipDetails() with value "
				+ "- List<AccountDiscoveryFipRequestIdentifierDTO> : {}",identifierDTOList);

		return identifierDTOList;
	}

	public FipDetailsForFipDTO mapFipEntityToFipDetails(RegistryEntity fip) {
		log.info("Executing MapperUtil.mapFipEntityToFipDetails() with Param - RegistryEntity: {}"
				+"- Mapping a FipEntity from registry to FipDetails DTO.",fip);
		
		FipDetailsForFipDTO fipDetailsDTO=new FipDetailsForFipDTO();
        fipDetailsDTO.setId(fip.getBusinessId());
        fipDetailsDTO.setName(fip.getName());
        fipDetailsDTO.setFiTypes(DataUtil.getFiTypes());
        
		log.info("Returning from MapperUtil.mapFipEntityToFipDetails() with value "
				+ "- FipDetailsForFipDTO: {}",fipDetailsDTO);

		return fipDetailsDTO;
	}

	public AccountDiscoveryPollingAppResponseDTO mapAccountDiscoveryCacheRecordToAccountDiscoveryPollingAppResponse(
			AccountDiscoveryCacheRecord accountDiscoveryCacheRecord) {
		log.info("Executing MapperUtil.mapAccountDiscoveryCacheRecordToAccountDiscoveryPollingAppResponse() with Param - AccountDiscoveryCacheRecord: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",accountDiscoveryCacheRecord);
		
		AccountDiscoveryPollingAppResponseDTO accountDiscoveryPollingAppResponseDTO = new AccountDiscoveryPollingAppResponseDTO();
		
		List<FipForAppDTO> fipAccountDiscoveryAppResponseList = new ArrayList<>();
		
		List<FinancialInformationProvider> fips = accountDiscoveryCacheRecord.getFipList();
		
		if(null != fips) {
			for(FinancialInformationProvider fip:fips) {
				FipForAppDTO fipAccountDiscoveryAppResponseDTO = new FipForAppDTO();
				fipAccountDiscoveryAppResponseDTO.setDiscoveredAccounts
														(mapFinancialAccountsToDicoveredAccountsForApp(fip.getDiscoveredAccounts()));
				fipAccountDiscoveryAppResponseDTO.setId(fip.getBusinessId());
				fipAccountDiscoveryAppResponseDTO.setLogoUrl(fip.getLogoUrl());
				fipAccountDiscoveryAppResponseDTO.setName(fip.getName());
				
				fipAccountDiscoveryAppResponseList.add(fipAccountDiscoveryAppResponseDTO);
			}
		}
		
		accountDiscoveryPollingAppResponseDTO
					.setFipForAppList(fipAccountDiscoveryAppResponseList);
		
		accountDiscoveryPollingAppResponseDTO
					.setNumberOfSearchedFips(accountDiscoveryCacheRecord.getTotalFipsDiscovered());
		
		log.info("Returning from MapperUtil.mapAccountDiscoveryCacheRecordToAccountDiscoveryPollingAppResponse() with value - AccountDiscoveryPollingAppResponseDTO: {}",accountDiscoveryPollingAppResponseDTO);

		return accountDiscoveryPollingAppResponseDTO;
	}

	public List<DiscoveredAccountForAppDTO> mapFinancialAccountsToDicoveredAccountsForApp(
														List<FinancialAccount> discoveredAccounts) {
		log.info("Executing MapperUtil.mapFinancialAccountsToDicoveredAccountsForApp() with Param - List<FinancialAccount>: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",discoveredAccounts);
		
		List<DiscoveredAccountForAppDTO> discoverAccountAppList = new ArrayList<>();
		
		for(FinancialAccount discoveredAccount: discoveredAccounts) {
			DiscoveredAccountForAppDTO discoveredAccountForApp 
								= mapFinancialAccountToDicoveredAccountForApp(discoveredAccount);
			
			discoverAccountAppList.add(discoveredAccountForApp);
		}
		
		log.info("Returning from MapperUtil.mapFinancialAccountsToDicoveredAccountsForApp() with value "
				+ "- List<DiscoveredAccountForAppDTO>: {}",discoverAccountAppList);

		return discoverAccountAppList;
	}

	public DiscoveredAccountForAppDTO mapFinancialAccountToDicoveredAccountForApp(FinancialAccount discoveredAccount) {
		log.info("Executing MapperUtil.mapFinancialAccountToDicoveredAccountForApp() with Param - FinancialAccount: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",discoveredAccount);
		
		DiscoveredAccountForAppDTO discoveredAccountForApp = new DiscoveredAccountForAppDTO();
		
		discoveredAccountForApp.setAccountNumber(discoveredAccount.getAccountNumber());
		discoveredAccountForApp.setLinked(discoveredAccount.getLinked());
		discoveredAccountForApp.setAccountType(discoveredAccount.getAccountType());
		
		log.info("Returning from MapperUtil.mapFinancialAccountToDicoveredAccountForApp() with value "
				+ "- DiscoveredAccountForAppDTO: {}",discoveredAccountForApp);

		return discoveredAccountForApp;
	}

	public AccountDiscoveryCacheRecord mapAccountDiscoveryAppResponseToAccountDiscoveryCacheRecord(
			AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO) {
		log.info("Executing MapperUtil.mapAccountDiscoveryAppResponseToAccountDiscoveryCacheRecord() with Param - AccountDiscoveryAppResponseDTO: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",accountDiscoveryAppResponseDTO);
		
		AccountDiscoveryCacheRecord accountDiscoveryCacheRecord = new AccountDiscoveryCacheRecord();
		
		List<FipForAppDTO> fipForAppDTOList = accountDiscoveryAppResponseDTO.getFipForAppList();
		
		accountDiscoveryCacheRecord.setTotalFipsDiscovered(fipForAppDTOList.size());
		accountDiscoveryCacheRecord.setFipList(mapFipForAppListToFinanciaInformationProviderList(fipForAppDTOList));
		
		log.info("Returning from MapperUtil.mapAccountDiscoveryAppResponseToAccountDiscoveryCacheRecord() with value "
				+ "- List<FinancialInformationProvider>: {}",accountDiscoveryCacheRecord);

		return accountDiscoveryCacheRecord;
	}

	public List<FinancialInformationProvider> mapFipForAppListToFinanciaInformationProviderList(
			List<FipForAppDTO> fipForAppDTOList) {
		log.info("Executing MapperUtil.mapFipForAppListToFinanciaInformationProviderList() with Param - List<FipForAppDTO>: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",fipForAppDTOList);
		List<FinancialInformationProvider> fipList = new ArrayList<>();
		 
		for(FipForAppDTO fipForAppDTO:fipForAppDTOList) {
			FinancialInformationProvider fip = mapFipForAppToFinancialInformationProvider(fipForAppDTO);
			fipList.add(fip);
		}
		
		log.info("Returning from MapperUtil.mapFipForAppListToFinanciaInformationProviderList() with value - List<FinancialInformationProvider>: {}",fipList);

		return fipList;
	}

	public FinancialInformationProvider mapFipForAppToFinancialInformationProvider(FipForAppDTO fipForAppDTO) {
		log.info("Executing MapperUtil.mapFipForAppToFinancialInformationProvider() with Param - FipForAppDTO: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",fipForAppDTO);
		
		FinancialInformationProvider fip = new FinancialInformationProvider();
		
		fip.setBusinessId(fipForAppDTO.getId());
		fip.setName(fipForAppDTO.getName());
		fip.setLogoUrl(fipForAppDTO.getLogoUrl());
		fip.setDiscoveredAccounts(mapDiscoveredAccountForAppListToFinancialAccountList(fipForAppDTO.getDiscoveredAccounts()));
		
		log.info("Returning from MapperUtil.mapFipForAppToFinancialInformationProvider() with value - FinancialInformationProvider: {}",fip);
		
		return fip;
	}

	public List<FinancialAccount> mapDiscoveredAccountForAppListToFinancialAccountList(
			List<DiscoveredAccountForAppDTO> discoveredAccounts) {
		log.info("Executing MapperUtil.mapDiscoveredAccountForAppListToFinancialAccountList() with Param - 	List<DiscoveredAccountForAppDTO>: {}"
				+"- Mapping a list of DiscoveredAccountForAppDTO to a list of Financial Account data model classes.",discoveredAccounts);
		
		List<FinancialAccount> financialAccountList = new ArrayList<FinancialAccount>();
		 
		for(DiscoveredAccountForAppDTO discoveredAccount:discoveredAccounts) {
			FinancialAccount financialAccount = mapDiscoverdAccountsForAppToFinancialAccount(discoveredAccount);
			financialAccountList.add(financialAccount);
		}
		
		log.info("Returning from MapperUtil.mapDiscoveredAccountForAppListToFinancialAccountList() with value "
				+ "- List<FinancialAccount>: {}",financialAccountList);

		return financialAccountList;
	}

	public FinancialAccount mapDiscoverdAccountsForAppToFinancialAccount(DiscoveredAccountForAppDTO discoveredAccount) {
		log.info("Executing MapperUtil.mapDiscoverdAccountsForFipToFinancialAccount() with Param - DiscoveredAccountForAppDTO: {}"
				+"- Mapping DiscoveredAccountForApp DTO to the data model of a FinancialAccount.",discoveredAccount);
		
		FinancialAccount financialAccount = new FinancialAccount();
		
		financialAccount.setAccountNumber(discoveredAccount.getAccountNumber());
		financialAccount.setAccountType(discoveredAccount.getAccountType());
		financialAccount.setLinked(discoveredAccount.getLinked());
		
		log.info("Returning from MapperUtil.mapDiscoverdAccountsForFipToFinancialAccount() with Value - FinancialAccount: {}",financialAccount);
		
		return financialAccount;
	}

	public List<FipForAppDTO> mapFinanciaInformationProviderListToFipForAppList(
			List<FinancialInformationProvider> fips) {
		
		List<FipForAppDTO> fipForAppList = new ArrayList<>();
		
		for(FinancialInformationProvider fip: fips) {
			FipForAppDTO fipForAppDTO = mapFinanciaInformationProviderToFipForApp(fip);
			fipForAppList.add(fipForAppDTO);
		}
		return fipForAppList;
	}

	public FipForAppDTO mapFinanciaInformationProviderToFipForApp(FinancialInformationProvider fip) {
		FipForAppDTO fipForAppDTO = new FipForAppDTO();
		fipForAppDTO.setId(fip.getBusinessId());
		fipForAppDTO.setName(fip.getName());
		fipForAppDTO.setLogoUrl(fip.getLogoUrl());
		fipForAppDTO.setDiscoveredAccounts(mapFinancialAccountsToDicoveredAccountsForApp(fip.getDiscoveredAccounts()));
		
		return fipForAppDTO;
	}
}
