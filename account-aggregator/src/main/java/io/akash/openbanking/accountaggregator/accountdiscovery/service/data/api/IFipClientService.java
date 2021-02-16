package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;

public interface IFipClientService {
	AccountDiscoveryFipResponseDTO discoverAccounts(AccountDiscoveryFipRequestDTO accDiscoveryDTO, 
																	RegistryEntity fip);
}
