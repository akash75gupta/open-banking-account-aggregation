package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api;

import java.util.List;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryRequestAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;

public interface ICachingService {

	Integer publishAccountDiscoveryResponse(AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO);

	Integer updateCounter(AccountDiscoveryAppResponseDTO accountDiscoveryAppResponseDTO);

	Integer fetchCountOfSearchedFips(AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO);

	List<FinancialInformationProvider> fetchAccountDiscoveryResponse(AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO);

	Integer deleteAccountDiscoveryResponse(AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO,
			List<FinancialInformationProvider> fips);

	Integer cleanAccountDiscoveryCounterCache(AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryRequestAppDTO);

	Integer cleanAccountDiscoveryCache(AutoAccountDiscoveryRequestAppDTO autoAccountDiscoveryRequestAppDTO);
	
}
