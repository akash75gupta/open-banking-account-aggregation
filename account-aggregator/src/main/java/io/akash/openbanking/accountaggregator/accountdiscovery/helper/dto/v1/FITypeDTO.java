package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.FITypeNameEnum;

/**
 * Purpose: This class  holds necessary  details required by AA client for showing data to user .
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class FITypeDTO {
    /**
     * Represent finanical information type
     */
    private FITypeNameEnum name;

    /**
     * Represent the value that described the selected FItypes
     */
    private String value;

    public FITypeNameEnum getName() {
        return name;
    }

    public void setName(FITypeNameEnum name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
