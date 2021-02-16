package io.akash.accountaggregator.fipsimulator.model.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bank {
    private String id;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("logo_URL")
    private String logoUrl;

    @JsonProperty("website")
    private String website;
    
    @JsonProperty("branches")
    private List<Branch> branches;

    private ATMs atms;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	public ATMs getAtms() {
		return atms;
	}

	public void setAtms(ATMs atms) {
		this.atms = atms;
	}
}
