package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierCategoryEnum;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierTypeEnum;


/**
 * Purpose: This class  holds identifiers like mobile/aadhar details .
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryFipRequestIdentifierDTO {

    /**
     * reepresent the category of identifier
     */
    private IdentifierCategoryEnum category;

    /**
     * Represent the type of identifiers
     */
    private IdentifierTypeEnum type;

    /**
     * Represent the value of identifiers
     */
    private String value;

    public IdentifierCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(IdentifierCategoryEnum category) {
        this.category = category;
    }

    public IdentifierTypeEnum getType() {
        return type;
    }

    public void setType(IdentifierTypeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
