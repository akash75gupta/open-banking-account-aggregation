package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierTypeEnum;

/**
 * Purpose:- 
 * This is a model class that stores the identification information of a customer.
 * 
 *
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02 
 *
 **/

public class Identifier {
    private IdentifierTypeEnum type;
    private String 	value;
    private Boolean isVerified;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	public IdentifierTypeEnum getType() {
		return type;
	}
	public void setType(IdentifierTypeEnum type) {
		this.type = type;
	}
 }
