package io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration;


/**
 * Purpose: Enum class for maintaining AccountType.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public enum AccountTypeEnum {
	SAVINGS("SAVINGS"),
	CURRENT("CURRENT"),
	NRE("NRE"),
	NRO("NRO"),
	DEFAULT("DEFAULT");
	
	private final String value;
	
	private AccountTypeEnum(String value) {
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
