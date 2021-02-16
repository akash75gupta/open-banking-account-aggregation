package io.akash.accountaggregator.fipsimulator.model.entities;

import org.springframework.data.mongodb.core.mapping.Field;

public class AccountRouting {
	@Field("scheme")
	private String scheme;
	@Field("address")
    private String address;
    
    public AccountRouting(String scheme, String address) {
    	this.scheme = scheme;
    	this.address = address;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
