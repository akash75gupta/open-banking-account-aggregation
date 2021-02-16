package io.akash.accountaggregator.fipsimulator.model.dtos;

import javax.validation.constraints.NotNull;

import io.akash.accountaggregator.fipsimulator.model.enums.IdentifierCategory;
import io.akash.accountaggregator.fipsimulator.model.enums.IdentifierType;

public class IdentifierDTO {
	
	@NotNull(message = "category field in identifier is mandatory and cannot be null")
    private  IdentifierCategory category;
	
	@NotNull(message = "type field in identifier is mandatory and cannot be null")
    private  IdentifierType type;
	
	@NotNull(message = "value field in identifier is mandatory and cannot be null")
    private  String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public IdentifierCategory getCategory() {
		return category;
	}

	public void setCategory(IdentifierCategory category) {
		this.category = category;
	}

	public IdentifierType getType() {
		return type;
	}

	public void setType(IdentifierType type) {
		this.type = type;
	}
}
