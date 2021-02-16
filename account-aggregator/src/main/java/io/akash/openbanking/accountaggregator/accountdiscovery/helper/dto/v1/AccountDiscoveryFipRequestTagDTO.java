package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.TagNameEnum;


/**
 * Purpose:- 
 * This class hold name and value of appliances info like 
 * geolocation/os/location  .
 * 
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryFipRequestTagDTO {
    /**
     * Name of customer’s device information
     */
	@NotNull(message = "name field inside tag is manadatory and cannot be null")	
	@NotEmpty(message = "name field inside tag is mandatory and cannot be empty")
    private TagNameEnum name;

    /**
     * value of customer’s device information
     */
	@NotNull(message = "value field inside tag is manadatory and cannot be null")	
	@NotEmpty(message = "value field inside tag is mandatory and cannot be empty")
    private String value;

    public TagNameEnum getName() {
        return name;
    }

    public void setName(TagNameEnum name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
