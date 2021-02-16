package io.akash.accountaggregator.crsimulator.model.dtos;

import java.util.List;
import java.util.Set;

import io.akash.accountaggregator.crsimulator.model.Aggregator;
import io.akash.accountaggregator.crsimulator.model.Bank;
import io.akash.accountaggregator.crsimulator.model.NBFC;

import lombok.Data;

@Data
public class RegistryResponseDTO {
	private List<Bank> fips;
	private List<NBFC> fius;
	private List<Aggregator> accountAggregators;
	
	private Set<EntityDTO> entities;

	public Set<EntityDTO> getEntities() {
		return entities;
	}

	public void setEntities(Set<EntityDTO> entities) {
		this.entities = entities;
	}

	public List<Bank> getFips() {
		return fips;
	}

	public void setFips(List<Bank> fips) {
		this.fips = fips;
	}

	public List<NBFC> getFius() {
		return fius;
	}

	public void setFius(List<NBFC> fius) {
		this.fius = fius;
	}

	public List<Aggregator> getAccountAggregators() {
		return accountAggregators;
	}

	public void setAccountAggregators(List<Aggregator> accountAggregators) {
		this.accountAggregators = accountAggregators;
	}
}
