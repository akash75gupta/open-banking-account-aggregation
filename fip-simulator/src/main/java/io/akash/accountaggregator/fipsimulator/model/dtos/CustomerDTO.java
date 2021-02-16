package io.akash.accountaggregator.fipsimulator.model.dtos;

import javax.validation.constraints.NotNull;

public class CustomerDTO {
	@NotNull(message = "id field in customer is mandatory and cannot be null")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
