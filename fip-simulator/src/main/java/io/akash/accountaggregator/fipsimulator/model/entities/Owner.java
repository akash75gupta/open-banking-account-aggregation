package io.akash.accountaggregator.fipsimulator.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {
	
	@JsonProperty("provider")
	private String id;
	
	@JsonProperty("provider")
	private String provider;
	
	@JsonProperty("display_name")
	private String display_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}	
}
