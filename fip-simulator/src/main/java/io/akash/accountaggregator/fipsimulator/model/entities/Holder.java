package io.akash.accountaggregator.fipsimulator.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Holder {
	
	@JsonProperty("name")
	private String customerId;
	
	@JsonProperty("is_alias")
	private String authority;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
