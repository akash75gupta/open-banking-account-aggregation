package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Purpose:- 
 * This is a general purpose class used for mapping one type of object 
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02
 *  
 **/

@Document(collection = "user_account")
public class UserAccount {
    @Id
    private ObjectId id;
       
    private String customerId;
    
    // email, adhaar, phone, pan, crn and more
    private List<Identifier> identifiers; 
    
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
