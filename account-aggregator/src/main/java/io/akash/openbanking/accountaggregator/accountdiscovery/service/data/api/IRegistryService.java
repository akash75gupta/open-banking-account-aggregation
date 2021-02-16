package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api;

import java.util.List;

import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;

public interface IRegistryService {
	List<RegistryEntity> saveFips(List<RegistryEntity> registryEntities);
	List<RegistryEntity> getFips();
	RegistryEntity getFip(String id);
}
