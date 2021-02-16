package io.akash.accountaggregator.crsimulator.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.akash.accountaggregator.crsimulator.model.Aggregator;
import io.akash.accountaggregator.crsimulator.model.Bank;
import io.akash.accountaggregator.crsimulator.model.NBFC;
import io.akash.accountaggregator.crsimulator.model.dtos.EntityDTO;
import io.akash.accountaggregator.crsimulator.model.dtos.EntityTypeDTO;

public class DTOMapperUtil {
	
	public static Set<EntityDTO> mapBanksToEntities(Set<Bank> banks){
		Set<EntityDTO> entities = new HashSet<>(banks.size());
		
		entities = banks.stream().map(bank -> mapBankToEntity(bank))
                 .collect(Collectors.toSet());
		
		return entities; 
	}

	private static EntityDTO mapBankToEntity(Bank bank) {
		EntityDTO entity = new EntityDTO();
		
		entity.setType(EntityTypeDTO.FIP);
		entity.setId(bank.getId());
		entity.setName(bank.getName());
		entity.setPhones(bank.getPhones());
		entity.setEmails(bank.getEmails());
		entity.setApis(bank.getApis());
		entity.setDocs(bank.getDocs());
		entity.setKeys(bank.getKeys());
		
		return entity;
	}

	public static Set<EntityDTO> mapNBFCsToEntities(Set<NBFC> nbfcs){
		Set<EntityDTO> entities = new HashSet<>(nbfcs.size());
		
		entities = nbfcs.stream().map(nbfc -> mapNBFCToEntity(nbfc))
                 .collect(Collectors.toSet());
		
		return entities; 
	}

	private static EntityDTO mapNBFCToEntity(NBFC nbfc) {
		EntityDTO entity = new EntityDTO();
		
		entity.setType(EntityTypeDTO.FIU);
		entity.setId(nbfc.getId());
		entity.setName(nbfc.getName());
		entity.setPhones(nbfc.getPhones());
		entity.setEmails(nbfc.getEmails());
		entity.setApis(nbfc.getApis());
		entity.setDocs(nbfc.getDocs());
		entity.setKeys(nbfc.getKeys());
		
		return entity;
	}

	public static Set<EntityDTO> mapAggregatorsToEntities(Set<Aggregator> aggregators){
		Set<EntityDTO> entities = new HashSet<>(aggregators.size());
		
		entities = aggregators.stream().map(aggregator -> mapAggregatorToEntity(aggregator))
                 .collect(Collectors.toSet());
		
		return entities; 
	}

	private static EntityDTO mapAggregatorToEntity(Aggregator aggregator) {
		EntityDTO entity = new EntityDTO();
		
		entity.setType(EntityTypeDTO.AA);
		entity.setId(aggregator.getId());
		entity.setName(aggregator.getName());
		entity.setPhones(aggregator.getPhones());
		entity.setEmails(aggregator.getEmails());
		entity.setApis(aggregator.getApis());
		entity.setDocs(aggregator.getDocs());
		entity.setKeys(aggregator.getKeys());
		
		return entity;
	}
}
