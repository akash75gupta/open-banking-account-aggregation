package io.akash.accountaggregator.fipsimulator.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    @JsonProperty("line_1")
    private String street;
    private String city;
    private String postcode;
    private String country;
    
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
