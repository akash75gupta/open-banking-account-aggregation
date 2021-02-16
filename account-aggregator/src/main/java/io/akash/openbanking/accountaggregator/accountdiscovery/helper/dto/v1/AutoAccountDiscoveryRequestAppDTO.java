package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class AutoAccountDiscoveryRequestAppDTO extends RequestBaseDTO {
    /**
     * represent the meta info it received from AA client
     */
	@NotNull(message = "meta field is manadatory and cannot be null")	
    private AccountDiscoveryFipRequestMetaDTO meta;

	public AccountDiscoveryFipRequestMetaDTO getMeta() {
		return meta;
	}

	public void setMeta(AccountDiscoveryFipRequestMetaDTO meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return "AutoAccountDiscoveryRequestAppDTO [meta=" + meta + ", getVer()=" + getVer() + ", getTimestamp()="
				+ getTimestamp() + ", getCustomerId()=" + getCustomerId() + ", getTransactionId()=" + getTransactionId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
