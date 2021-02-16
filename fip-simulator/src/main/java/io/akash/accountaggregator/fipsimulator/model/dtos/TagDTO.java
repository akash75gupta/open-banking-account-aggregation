package io.akash.accountaggregator.fipsimulator.model.dtos;

import io.akash.accountaggregator.fipsimulator.model.enums.TagName;

public class TagDTO {
    private TagName name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public TagName getName() {
		return name;
	}

	public void setName(TagName name) {
		this.name = name;
	}
}
