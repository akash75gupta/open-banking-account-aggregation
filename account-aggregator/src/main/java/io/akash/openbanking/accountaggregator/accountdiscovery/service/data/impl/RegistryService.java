package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.GeneralConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.MapperUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RegistryEntityRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api.IRegistryService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/**
 * 
 * Purpose:- 
 * This is a caching service that uses the in memory data store, Redis, for caching data.
 * It fulfills the contract as defined by ICachingService
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-01
 * 
 **/

@Service
public class RegistryService implements IRegistryService {

    private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

	@Autowired
	RegistryEntityRepository registryEntityRepository;
	
	@Autowired
	MapperUtil mapperUtil;

    /**
     * @return returning the list of FIPs from database
     **/
	
    @Override
    public List<RegistryEntity> getFips(){
    	log.info("Executing AccountManagementService.getFips() without any Param:"
    			+"Fetching fips from DB");
    	
    	List<RegistryEntity> registryEntities = null;
    	
    	try {
    	    registryEntities= registryEntityRepository.findAll();
    	       
			if (registryEntities == null) {
				StringBuilder expMessageBuilder = new StringBuilder()
						.append("No record found!")
						.append(System.getProperty("line.separator"))
						.append("RCA: There are no FIPs in the DB.");
				throw new CustomException(HttpStatus.NOT_FOUND, expMessageBuilder.toString());
			}
    	      
    	}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Unrecognized exception! Need to handle.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					expMessageBuilder.toString(),e);
		}
    	
        log.info("Returning from AccountManagementService.getFips() with Value - fips(List<RegistryEntity>): {}",registryEntities);
        
        return registryEntities;
    }
    
    /**
     * @param  id represent FIP id
     * @return FipDetails for the corresponding id
     * 
     **/
    
    @Override
	public RegistryEntity getFip(String id) {
		log.info("Executing AccountManagementService.getFip() with Param id(String): {}" 
				+"- Fetching FIP details from registry",id);
		
		RegistryEntity fip = null;

		try {
			fip = registryEntityRepository.findByBusinessId(id);
			
			if (fip == null) {
				StringBuilder expMessageBuilder = new StringBuilder().append("No record found!")
						.append(System.getProperty("line.separator")).append("RCA: There are no FIPs in the DB.");
				throw new CustomException(HttpStatus.NOT_FOUND, expMessageBuilder.toString());
			}	
		}catch(CustomException e) {
			throw e;
		}catch (Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Unrecognized exception! Need to handle.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					expMessageBuilder.toString(),e);
		}

		log.info("Returning from AccountManagementService.getFip() with value - fip(RegistryEntity): {}", fip);
	
		return fip;
	}
   
	@Override 
	public List<RegistryEntity> saveFips(List<RegistryEntity> registryEntities) {
		log.info("Executing AccountManagementService.saveFips() with Param - registryEntities(String): {}" 
				+ "- Saving the fetched fips from registry to our database",registryEntities);
		
		List<RegistryEntity> fips = new ArrayList<RegistryEntity>();  
		try {
			for (RegistryEntity registryEntity : registryEntities) {
				if (registryEntity.getType().equals(GeneralConstants.TYPE_FIP)) {
					fips.add(registryEntity);
					RegistryEntity savedRegistryEntity = registryEntityRepository.save(registryEntity);
					
					if(savedRegistryEntity == null) {
						StringBuilder expMessageBuilder = new StringBuilder()
														  .append("The given FIP entity- "+registryEntity+" could not saved into the DB.");
						throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,expMessageBuilder.toString());
					}
				}
			}
		}catch(CustomException e) {
				throw e;
		}catch(Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Unrecognized exception! Need to handle.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					expMessageBuilder.toString(),e);
		}
		
		log.info("Returning from AccountManagementService.saveFips() with value - fips(List<RegistryEntity>): {}", fips);
		
		return fips;
	}
}
