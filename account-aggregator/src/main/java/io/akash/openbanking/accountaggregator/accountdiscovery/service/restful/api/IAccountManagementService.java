package io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.api;


import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryPollingAppResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryRequestAppDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AutoAccountDiscoveryResponseAppDTO;

/** 
 * This interface defines all the services pertaining to account discovery feature.
 * 
 * @author , Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-03-27
 **/

public interface IAccountManagementService {

	/**
	 * @param accountDiscoveryAppRequestDTO
	 * @return
	 */
	
	AccountDiscoveryAppResponseDTO discoverAccounts(AccountDiscoveryAppRequestDTO accountDiscoveryAppRequestDTO);
    

    /**
     * @return it will return the necessary detials required by aa client after fetching all the updated fips details from central registry
     * @throws Exception 
     */

	AutoAccountDiscoveryResponseAppDTO autoDiscoverAccounts
			(AutoAccountDiscoveryRequestAppDTO reqRecAccDiscoveryDTO);
	
	AccountDiscoveryPollingAppResponseDTO pollCache(
			AccountDiscoveryPollingAppRequestDTO accountDiscoveryPollingAppRequestDTO);
}
