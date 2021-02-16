package io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration;


/**
 * Purpose: Enum class for maintaining AuthenticatorType.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public enum AuthenticatorTypeEnum {
	DIRECT("DIRECT"), 
	TOKEN("DIRECT");
	
	private final String value;
	
	private AuthenticatorTypeEnum(String value) {
          this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String value() {
        return this.value;
    }
}
