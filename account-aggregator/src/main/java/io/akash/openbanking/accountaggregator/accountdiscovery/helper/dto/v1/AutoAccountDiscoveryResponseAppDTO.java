package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import org.springframework.stereotype.Component;

@Component
public class AutoAccountDiscoveryResponseAppDTO extends ResponseBaseDTO {
	private Integer fipCount;

	public Integer getFipCount() {
		return fipCount;
	}

	public void setFipCount(Integer fipCount) {
		this.fipCount = fipCount;
	}

	@Override
	public String toString() {
		return "AutoAccountDiscoveryResponseAppDTO [fipCount=" + fipCount + ", getVer()=" + getVer()
				+ ", getTimestamp()=" + getTimestamp() + ", getCustomerId()=" + getCustomerId()
				+ ", getTransactionId()=" + getTransactionId() + "]";
	}
}
