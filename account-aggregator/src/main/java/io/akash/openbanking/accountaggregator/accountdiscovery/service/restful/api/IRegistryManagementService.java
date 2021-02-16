package io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.api;

import java.util.List;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FIPRegistryAppResponseDTO;

public interface IRegistryManagementService {
	List<FIPRegistryAppResponseDTO> getFips();
}
