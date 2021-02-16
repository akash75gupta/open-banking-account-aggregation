package io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration;


/**
 * Purpose: Enum class for maintaining FItypes.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public enum FITypeNameEnum {

	DEPOSIT("DEPOSIT"), 
	TERM_DEPOSIT("TERM_DEPOSIT"), 
	RECURRING_DEPOSIT("RECURRING_DEPOSIT"), 
	SIP("SIP"), 
	CP("CP"), 
	GOVT_SECURITIES("GOVT_SECURITIES"), 
	EQUITIES("EQUITIES"), 
	BONDS("BONDS"), 
	DEBENTURES("DEBENTURES"), 
	MUTUAL_FUNDS("MUTUAL_FUNDS"), 
	ETF("ETF"), 
	IDR("IDR"), 
	CIS("CIS"), 
	AIF("AIF"), 
	INSURANCE_POLICIES("INSURANCE_POLICIES"), 
	NPS("NPS"), 
	INVIT("INVIT"), 
	REIT("REIT"),
	OTHER("OTHER");

	private final String value;
	
	private FITypeNameEnum(String value) {
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
