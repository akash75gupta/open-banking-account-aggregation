package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

/**
 * Purpose:- 
 * This is a general purpose class used for mapping one type of object 
 * to another type of object as per business use cases.
 * 
 *
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-10 
 */

public class RegistryEntityKeyDTO {

    /**
     * represent the expiry of key
     */
    private String expiry;

    /**
     * represent the value of key
     */
    private String value;

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeysFromRegistryDTO{" +
                "expiry='" + expiry + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
