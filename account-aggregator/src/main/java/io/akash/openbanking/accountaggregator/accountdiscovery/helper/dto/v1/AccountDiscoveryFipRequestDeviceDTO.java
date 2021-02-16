package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Purpose: 
 * This class  holds list of tags(mobile configuration).
 * 
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryFipRequestDeviceDTO {
    /**
     * Represent the list of meta info received from AA client
     */
	@NotNull(message = "tags field inside deviceInfo is manadatory and cannot be null")	
	@NotEmpty(message = "tags field inside deviceInfo is mandatory and cannot be empty")
    private List<AccountDiscoveryFipRequestTagDTO> tags;

    public List<AccountDiscoveryFipRequestTagDTO> getTags() {
        return tags;
    }

    public void setTags(List<AccountDiscoveryFipRequestTagDTO> tags) {
        this.tags = tags;
    }
}
