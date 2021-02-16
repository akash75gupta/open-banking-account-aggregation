package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.AccountTypeEnum;

/**
 * Purpose: 
 * This class is used for transferring a discovered account retrieved from a FIP, to the client AA App 
 * 
 * @author , Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-01
 **/

public class DiscoveredAccountForAppDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8342645712925291153L;

	/**
     * represent account type
     */
    private AccountTypeEnum accountType;

    /**
     * represent account No
     */
    private String accountNumber;

    /**
     * represent status of account either linked or not
     */
    
    private Boolean isLinked;

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    @JsonProperty("isLinked")
    public Boolean getLinked() {
        return isLinked;
    }

    public void setLinked(Boolean linked) {
        isLinked = linked;
    }

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "AccountAppDTO [accountType=" + accountType + ", accountNumber=" + accountNumber + ", isLinked="
				+ isLinked + "]";
	}
}
