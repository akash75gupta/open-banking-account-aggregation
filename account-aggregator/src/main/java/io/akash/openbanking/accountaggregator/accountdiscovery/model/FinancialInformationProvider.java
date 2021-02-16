package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Purpose:- 
 * The model of a Financial Information Provider (FIP)
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-16
 * 
 **/

public class FinancialInformationProvider implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 969185551701996840L;
	
	private String businessId;
    /**
     * represent name of FIP
     */
    private String name;

    /**
     * represent logoUrl
     */
    private String logoUrl;

    /**
     * represent customized discovered account needed for AA client
     */
    private List<FinancialAccount> discoveredAccounts;

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public List<FinancialAccount> getDiscoveredAccounts() {
		return discoveredAccounts;
	}

	public void setDiscoveredAccounts(List<FinancialAccount> discoveredAccounts) {
		this.discoveredAccounts = discoveredAccounts;
	}

	@Override
	public String toString() {
		return "FinancialInformationProvider [businessId=" + businessId + ", name=" + name + ", logoUrl=" + logoUrl
				+ ", discoveredAccounts=" + discoveredAccounts + "]";
	}
}
