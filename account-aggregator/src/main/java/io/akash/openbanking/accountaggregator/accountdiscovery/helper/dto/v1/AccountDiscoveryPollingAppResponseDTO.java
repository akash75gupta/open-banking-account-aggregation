package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class AccountDiscoveryPollingAppResponseDTO extends ResponseBaseDTO{
	
	@JsonProperty("numberOfSearchedFips")
	private Integer numberOfSearchedFips = 0;
	
	@JsonProperty("fips")
	private List<FipForAppDTO> fipForAppList = new ArrayList<FipForAppDTO>();

	public Integer getNumberOfSearchedFips() {
		return numberOfSearchedFips;
	}

	public void setNumberOfSearchedFips(Integer totalFipDiscovered) {
		this.numberOfSearchedFips = totalFipDiscovered;
	}
	
	public void setFipForAppList(List<FipForAppDTO> fipAccountDiscoveryAppResponseList) {
		this.fipForAppList = fipAccountDiscoveryAppResponseList;
	}
	
	public List<FipForAppDTO> getFipForAppList() {
		return fipForAppList;
	}

	@Override
	public String toString() {
		return "AccountDiscoveryPollingAppResponseDTO [numberOfSearchedFips=" + numberOfSearchedFips
				+ ", fipForAppList=" + fipForAppList + ", getVer()=" + getVer() + ", getTimestamp()=" + getTimestamp()
				+ ", getCustomerId()=" + getCustomerId() + ", getTransactionId()=" + getTransactionId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
