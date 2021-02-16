package io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration;


/**
 * Purpose: Enum class for maintaining identifier type.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public enum IdentifierTypeEnum {
	MOBILE("MOBILE"),
	AADHAR("AADHAR"),
	PAN("PAN"),
	EMAIL("EMAIL"),
	DOB("DOB"),
	ACCNO("ACCNO"), 
	CRN("CRN");
	
	private final String value;
	
	private IdentifierTypeEnum(String value) {
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
