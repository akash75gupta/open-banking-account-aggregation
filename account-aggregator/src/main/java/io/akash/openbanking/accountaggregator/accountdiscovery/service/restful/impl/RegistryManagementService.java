package io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.akash.openbanking.accountaggregator.accountdiscovery.controller.AccountDiscoveryController;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FIPRegistryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.MapperUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl.RegistryService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.api.IRegistryManagementService;

@Service
public class RegistryManagementService implements IRegistryManagementService{

	private static final Logger log= LoggerFactory.getLogger(AccountDiscoveryController.class);
	
	@Autowired
	RegistryService registryService;
	
	@Autowired
	MapperUtil mapperUtil;
	
	@Override
	public List<FIPRegistryAppResponseDTO> getFips() {
		log.info("Executing AccountManagementService.getFips() without any params:"
			  +  "Retrieving all the fips along with their details");
		
		List<RegistryEntity> fipList = registryService.getFips();
		
		List<FIPRegistryAppResponseDTO> fipForAppList  = mapperUtil.mapRegistryEntityListToFipForAppList(fipList);

		log.info("Returning from AccountManagementService.getFips():"
				+ "Value - fips(List<FIPRegistryAppResponseDTO>): {}", fipForAppList);
		
		return fipForAppList;
	}
}
