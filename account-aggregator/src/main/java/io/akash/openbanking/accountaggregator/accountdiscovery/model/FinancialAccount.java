package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import java.io.Serializable;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.AccountTypeEnum;

/**
 * 
 * Purpose:- 
 * The model of a Financial Account of a Customer. A Financial Account can be of different types 
 * namely- Bank Account, Investment Account, Loan Account etc.
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-16
 * 
 **/

public class FinancialAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1557315210262314579L;
	
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
    
    private String referenceNumber;

	public AccountTypeEnum getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeEnum accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Boolean getLinked() {
		return isLinked;
	}

	public void setLinked(Boolean isLinked) {
		this.isLinked = isLinked;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}
