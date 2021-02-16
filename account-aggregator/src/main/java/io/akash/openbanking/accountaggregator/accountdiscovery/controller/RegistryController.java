package io.akash.openbanking.accountaggregator.accountdiscovery.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FIPRegistryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.RegistryManagementService;

@RestController
@RequestMapping("/registry")
public class RegistryController {
    
	private static final Logger log= LoggerFactory.getLogger(AccountDiscoveryController.class);

	@Autowired
	RegistryManagementService registryManagementService; 
	
    @CrossOrigin
    @GetMapping(path="/fips")
    public ResponseEntity<List<FIPRegistryAppResponseDTO>> getFips(){
        log.info("Executing AccountDiscoveryController.getListOfFips()) without any params - "
        		+"Request the AccountManagementService for getting all the fip details"
        		+"(only necessary details required by AA client) from Database");

        List<FIPRegistryAppResponseDTO> fipRegistryAppResponseDtoList = null;

		fipRegistryAppResponseDtoList = registryManagementService.getFips();

        log.info("Returning from AccountDiscoveryController.getListOfFips())" +
                  "with value fipRegistryAppResponseDtoList: {}",fipRegistryAppResponseDtoList);
        
        return new ResponseEntity<List<FIPRegistryAppResponseDTO>>(fipRegistryAppResponseDtoList,HttpStatus.OK);
    }
}
