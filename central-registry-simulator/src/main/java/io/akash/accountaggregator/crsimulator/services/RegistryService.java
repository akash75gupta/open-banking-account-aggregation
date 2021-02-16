package io.akash.accountaggregator.crsimulator.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.akash.accountaggregator.crsimulator.model.Aggregator;
import io.akash.accountaggregator.crsimulator.model.Bank;
import io.akash.accountaggregator.crsimulator.model.NBFC;
import io.akash.accountaggregator.crsimulator.model.dtos.EntityDTO;
import io.akash.accountaggregator.crsimulator.model.dtos.RegistryResponseDTO;
import io.akash.accountaggregator.crsimulator.repositories.AggregatorRepository;
import io.akash.accountaggregator.crsimulator.repositories.BankRepository;
import io.akash.accountaggregator.crsimulator.repositories.NBFCRepository;
import io.akash.accountaggregator.crsimulator.utils.DTOMapperUtil;

@Service
public class RegistryService {
	
	@Autowired
	BankRepository bankRepo;
	
	@Autowired
	NBFCRepository nbfcRepo;
	
	@Autowired
	AggregatorRepository aggregatorRepo;
	
	public RegistryResponseDTO getRegistry() {
		RegistryResponseDTO registryResponse = new RegistryResponseDTO();
		
		Set<Bank> banks = new HashSet<>(bankRepo.findAll());
		Set<NBFC> nbfcs =  new HashSet<>(nbfcRepo.findAll());
		Set<Aggregator> aggregators = new HashSet<>(aggregatorRepo.findAll());
	
//		registryResponse.setAccountAggregators(accountAggregators);
//		registryResponse.setFius(fius);
//		registryResponse.setFips(fips);
		
		Set<EntityDTO> entities = new HashSet<>();
		
		entities.addAll(DTOMapperUtil.mapBanksToEntities(banks));
		entities.addAll(DTOMapperUtil.mapNBFCsToEntities(nbfcs));
		entities.addAll(DTOMapperUtil.mapAggregatorsToEntities(aggregators));
		
		registryResponse.setEntities(entities);
		
		return registryResponse;
	}	
}
