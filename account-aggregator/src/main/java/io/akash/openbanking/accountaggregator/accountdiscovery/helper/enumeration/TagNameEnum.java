package io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration;


/**
 * Purpose: Enum class for maintaining types of tag.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public enum TagNameEnum {
	
	GEOCODE("GEOCODE"),
	TELECOM("TELECOM"),
	LOCATION("LOCATION"),
	IP("IP"),
	TYPE("TYPE"),
	ID("ID"),
	OS("OS"),
	APP("APP"),
	MOBILE("MOBILE"),
	CAPABILITY("CAPABILITY");

	
	private final String value;
	
	private TagNameEnum(String value) {
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
