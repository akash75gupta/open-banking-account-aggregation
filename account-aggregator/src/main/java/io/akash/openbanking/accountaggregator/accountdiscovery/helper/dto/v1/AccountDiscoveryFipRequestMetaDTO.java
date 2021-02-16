package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Purpose: This class  holds  meta information about the appliances used by user .
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryFipRequestMetaDTO {

    /**
     *Specify the type of group about the meta-information is described
     */
	@NotNull(message = "type field inside meta is manadatory and cannot be null")	
	@NotBlank(message = "type field inside meta is manadatory and cannot be blank")	
    private String type;

    /**
     *Value of the group about the meta-information is described
     */
	@NotNull(message = "value field inside meta is manadatory and cannot be null")	
	@NotBlank(message = "value field inside meta is manadatory and cannot be blank")
    private String value;

    /**
     * Meta-information about the customer device
     */
	@NotNull(message = "deviceInfo field inside meta is manadatory and cannot be null")	
    private AccountDiscoveryFipRequestDeviceDTO deviceInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AccountDiscoveryFipRequestDeviceDTO getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(AccountDiscoveryFipRequestDeviceDTO deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
