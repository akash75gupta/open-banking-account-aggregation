package io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration;


/**
 * Purpose: Enum class for maintaining Identifier category.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public enum IdentifierCategoryEnum {
	STRONG("STRONG"),
	WEAK("WEAK"),
	ANCILLARY("ANCILLARY");

	private final String value;
	
	private IdentifierCategoryEnum(String value) {
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
