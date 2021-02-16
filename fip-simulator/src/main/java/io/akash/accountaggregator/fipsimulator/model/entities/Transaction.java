package io.akash.accountaggregator.fipsimulator.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="transactions")
public class Transaction {
	
	@Id
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("other_account")
    private Account destinationAccount;

    @JsonProperty("this_account")
    private Account sourceAccount;

    @JsonProperty("details")
    private Details details;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
