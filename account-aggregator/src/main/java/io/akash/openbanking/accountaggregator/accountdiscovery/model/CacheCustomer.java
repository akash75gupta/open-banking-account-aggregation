package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryAppResponseDTO;

/**
 * Purpose:- 
 * This object is used for storing a customer along with its discovered account in the cache.
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02 
 * 
 **/

@Component
@Document("cache")
public class CacheCustomer {
	@Id
	@Field("_id")
	private String customerId;
	
	private List<AccountDiscoveryAppResponseDTO> fips;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<AccountDiscoveryAppResponseDTO> getFips() {
		if(null == fips) {
			fips = new ArrayList<>();
		}
		return fips;
	}

	public void setFips(List<AccountDiscoveryAppResponseDTO> fips) {
		this.fips = fips;
	}
}
