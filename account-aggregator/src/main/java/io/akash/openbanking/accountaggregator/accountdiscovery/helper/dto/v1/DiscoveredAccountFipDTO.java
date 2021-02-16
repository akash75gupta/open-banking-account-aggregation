package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.AccountTypeEnum;

/**
 * Purpose:- 
 * This class  holds the discovered accounts retrieved from a FIP.
 * 
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class DiscoveredAccountFipDTO {

    /**
     * represent the financial information type
     */
    private FITypeDTO fiType;

    /**
     * represent an account type
     */
    private AccountTypeEnum accType;

    /**
     * Specify an account reference number that is refers to a customer’s account
     */
    private String accRefNumber;

    /**
     * Specify a masked account number
     */
    private String maskedAccNumber;

    public FITypeDTO getFiType() {
        return fiType;
    }

    public void setFiType(FITypeDTO fiType) {
        this.fiType = fiType;
    }

    public AccountTypeEnum getAccType() {
        return accType;
    }

    public void setAccType(AccountTypeEnum accType) {
        this.accType = accType;
    }

    public String getAccRefNumber() {
        return accRefNumber;
    }

    public void setAccRefNumber(String accRefNumber) {
        this.accRefNumber = accRefNumber;
    }

    public String getMaskedAccNumber() {
        return maskedAccNumber;
    }

    public void setMaskedAccNumber(String maskedAccNumber) {
        this.maskedAccNumber = maskedAccNumber;
    }
}
