package io.akash.accountaggregator.crsimulator.model.dtos;

import lombok.Data;

@Data
public class KeyDTO {
	private String value;
	private String expiry;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	
	
}
