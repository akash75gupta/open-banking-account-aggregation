package io.akash.openbanking.accountaggregator.accountdiscovery.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.GeneralConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryRequestAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryResponseAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.api.IAccountManagementService;

/**
 * Purpose:- 
 * This is a controller class that routes different incoming requests to the appropriate services.
 * 
 * @author Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-03-27
 * 
 **/

@RestController
@RequestMapping("/discover")
public class AccountDiscoveryController {

    private static final Logger log= LoggerFactory.getLogger(AccountDiscoveryController.class);

    @Autowired
    private IAccountManagementService accountManagementService;

    /**
     * This API receive request to find all the account of given customer present in all FIPS
     * 
     * @param accountDiscoveryAppRequestDTO it contain identifiers and Meta Data
     * @return AccDiscoveredDTO  It contains list of all accounts registered to FIP
     * @throws Exception 
     * 
     **/
    
    @CrossOrigin
    @PostMapping(path="/manual/accounts", consumes= GeneralConstants.APPLICATION_JSON_CONTENT_TYPE,
            produces=GeneralConstants.APPLICATION_JSON_CONTENT_TYPE)
    public ResponseEntity<AccountDiscoveryAppResponseDTO> discoverAccounts(
    		@RequestBody @Valid AccountDiscoveryAppRequestDTO accountDiscoveryAppRequestDTO ) {
        log.info("Executing AccountDiscoveryController.discoverAccounts() with param accountDiscoveryAppRequestDTO: {}" +
                 "Routing the discovery request to AccountManagementService to discover accounts with a specific fip.",accountDiscoveryAppRequestDTO);
       
       AccountDiscoveryAppResponseDTO accountDiscoveryAppResponse
       												= accountManagementService.discoverAccounts(accountDiscoveryAppRequestDTO);
      
       log.info("Returning from AccountDiscoveryController.discoverAccounts() with value: {}",accountDiscoveryAppResponse);
       
       return new ResponseEntity<AccountDiscoveryAppResponseDTO>(accountDiscoveryAppResponse,HttpStatus.OK);
    }


    /**
     * This Api receive request to find all the account of given customer present in all FIPS
     * 
     * @param autoAccountDiscoveryRequestAppDTO it contain identfiers and Meta Data
     * @return AccDiscoveredDTO  It contains list of all accounts registered to FIP
     */
    
    @CrossOrigin
    @PostMapping(path="/auto/accounts", consumes= GeneralConstants.APPLICATION_JSON_CONTENT_TYPE,
            produces=GeneralConstants.APPLICATION_JSON_CONTENT_TYPE)
    public ResponseEntity<AutoAccountDiscoveryResponseAppDTO> autoDiscoverAccounts(
    		 @RequestBody @Valid AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryRequestAppDTO) {
    	
    	log.info( "Executing AccountDiscoveryController.autoDiscoverAccounts()"
    			+ "with Param autoAccountDiscoveryRequestAppDTO: {}"
    			+ "Fetching all accounts of the given user automatically from all the enlisted FIPs",autoAccountDiscoveryRequestAppDTO);
    	
		AutoAccountDiscoveryResponseAppDTO autoAccountDiscoveryResponseAppDTO = null;
		
		autoAccountDiscoveryResponseAppDTO = accountManagementService.autoDiscoverAccounts(autoAccountDiscoveryRequestAppDTO);
		
		log.info("Returning from AccountDiscoveryController.autoDiscoverAccounts() "
				+"with value autoAccountDiscoveryResponseAppDTO: {}",autoAccountDiscoveryResponseAppDTO);
		
		return new ResponseEntity<AutoAccountDiscoveryResponseAppDTO>(autoAccountDiscoveryResponseAppDTO,HttpStatus.OK);
	}

    @CrossOrigin
    @PostMapping(path="/poll/accounts", consumes= GeneralConstants.APPLICATION_JSON_CONTENT_TYPE,
            produces=GeneralConstants.APPLICATION_JSON_CONTENT_TYPE)
    public ResponseEntity<AccountDiscoveryPollingAppResponseDTO> getAccountsFromCache(
    		 @RequestBody @Valid AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO) {
    	
    	log.info( "Executing AccountDiscoveryController.pollForDiscoveredAccounts()"
    			+ "with Param accountDiscoveryPollingAppRequestDTO: {}"
    			+"-Polling the cache for discovered accounts",accountDiscoveryPollingAppRequestDTO);
    	
		AccountDiscoveryPollingAppResponseDTO accountDiscoveryPollingAppResponseDTO = null;
		
		accountDiscoveryPollingAppResponseDTO = accountManagementService.pollCache(accountDiscoveryPollingAppRequestDTO);
		
    	log.info("Returning from AccountDiscoveryController.pollForDiscoveredAccounts()"
    			+ "with value accountDiscoveryPollingAppResponseDTO: {}",accountDiscoveryPollingAppRequestDTO);
    	
		return new ResponseEntity<>(accountDiscoveryPollingAppResponseDTO,HttpStatus.OK);
	}

}
