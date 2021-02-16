package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Purpose:- 
 * The model of a record stored in the cache for an Account Discovery request.
 * 
 * @author Akash Gupta (akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-16
 * 
 **/

public class AccountDiscoveryCacheRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2652679085373254511L;

	private Integer totalFipsDiscovered;
	
	private List<FinancialInformationProvider> fipList;

	public List<FinancialInformationProvider> getFipList() {
		return fipList;
	}
	
	public void setFipList(List<FinancialInformationProvider> fipList) {
		this.fipList = fipList;
	}
	
	public Integer getTotalFipsDiscovered() {
		return totalFipsDiscovered;
	}

	public void setTotalFipsDiscovered(Integer totalFipsDiscovered) {
		this.totalFipsDiscovered = totalFipsDiscovered;
	}

	@Override
	public String toString() {
		return "AccountDiscoveryCacheRecord [totalFipsDiscovered=" + totalFipsDiscovered + ", fipList=" + fipList + "]";
	}
}
