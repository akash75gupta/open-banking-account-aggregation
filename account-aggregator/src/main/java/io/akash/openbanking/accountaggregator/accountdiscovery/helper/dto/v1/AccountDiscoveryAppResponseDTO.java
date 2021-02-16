package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the response information to be sent to the client APP in response to account discovery request 
 * It contains a list of FIPs each of which in turn contains the discovered accounts corresponding to them. 
 * 
 * @author Akash Gupta(akash75gupta@gmail.com)
 * @author 
 * @version 1.0
 * @since   2019-03-29
 **/

@Component
public class AccountDiscoveryAppResponseDTO extends ResponseBaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3868402377754726135L;
	
	@JsonProperty("fips")
	private List<FipForAppDTO> fipForAppList;
	
	@JsonProperty("fips")
	public List<FipForAppDTO> getFipForAppList() {
		return fipForAppList;
	}
	
	@JsonProperty("fips")
	public void setFipForAppList(List<FipForAppDTO> fipAccountDiscoveryAppResponseList) {
		this.fipForAppList = fipAccountDiscoveryAppResponseList;
	}

	@Override
	public String toString() {
		return "AccountDiscoveryAppResponseDTO [fipForAppList=" + fipForAppList + ", getVer()=" + getVer()
				+ ", getTimestamp()=" + getTimestamp() + ", getCustomerId()=" + getCustomerId()
				+ ", getTransactionId()=" + getTransactionId() + "]";
	}
}
