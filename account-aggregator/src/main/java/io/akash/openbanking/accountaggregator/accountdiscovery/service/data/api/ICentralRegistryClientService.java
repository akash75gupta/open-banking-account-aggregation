package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.RegistryDTO;

public interface ICentralRegistryClientService {
	RegistryDTO getRegistry();
}
