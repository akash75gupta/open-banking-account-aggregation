package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Purpose: This class  is used the hold the request coming from AA client for account discovery request  .
 * @author , Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-03-27
 */

public class AccountDiscoveryAppRequestDTO extends RequestBaseDTO {
    
    /**
     * represent the list of identifiers it received from AA client
     */
	
    @NotNull(message = "identifiers field is manadatory and cannot be null")
    @NotEmpty(message = "identifiers field is manadatory and cannot be empty")
    private List<AccountDiscoveryAppRequestIdentifierDTO> identifiers;

    /**
     * represent the meta info it received from AA client
     */
    @NotNull(message = "meta field is manadatory and cannot be null")
    private AccountDiscoveryFipRequestMetaDTO meta;

    /**
     * represent the fipId
     */
    private String fipId;

    public List<AccountDiscoveryAppRequestIdentifierDTO> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<AccountDiscoveryAppRequestIdentifierDTO> identifiers) {
        this.identifiers = identifiers;
    }

    public AccountDiscoveryFipRequestMetaDTO getMeta() {
        return meta;
    }

    public void setMeta(AccountDiscoveryFipRequestMetaDTO meta) {
        this.meta = meta;
    }

    public String getFipId() {
        return fipId;
    }

    public void setFipId(String fipId) {
        this.fipId = fipId;
    }

	@Override
	public String toString() {
		return "AccountDiscoveryAppRequestDTO [identifiers=" + identifiers + ", meta=" + meta + ", fipId=" + fipId
				+ ", getVer()=" + getVer() + ", getTimestamp()=" + getTimestamp() + ", getCustomerId()="
				+ getCustomerId() + ", getTransactionId()=" + getTransactionId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}

