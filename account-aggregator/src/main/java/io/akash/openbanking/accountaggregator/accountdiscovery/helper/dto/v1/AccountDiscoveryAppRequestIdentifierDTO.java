package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierTypeEnum;

/**
 * This class  holds identifiers like mobile/aadhar details received from AA client.
 * 
 * @author  
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryAppRequestIdentifierDTO {

    /**
     * Represent the type of identifiers
     **/

	@NotNull(message  = "type field inside identifier is mandatory and cannot be null")
    @NotBlank(message = "type field inside identifier is mandatory and cannot be empty")
    private IdentifierTypeEnum type;

    /**
     * Represent the value of identifiers
     */
	
	@NotNull(message  = "value field inside identifier is mandatory and cannot be null")
    @NotBlank(message = "value field inside identifier is mandatory and cannot be empty")
    private String value;

    public IdentifierTypeEnum getType() {
        return type;
    }

    public void setType(IdentifierTypeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return "AccountDiscoveryAppRequestIdentifierDTO [type=" + type + ", value=" + value + "]";
	}
}
