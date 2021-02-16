package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierCategoryEnum;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.IdentifierTypeEnum;

/**
 * Purpose:- 
 * Holds specfications pertaining to the identifier dto as defined by ReBit like categories, types etc.
 * 
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02 
 */


@Component
@Document(collection = "identifier_specification")
public class IdentifierSpecification {
	@Id
	private ObjectId id;
	/*
	 * Represent the type of identifiers
	 */
	private IdentifierTypeEnum type;

	/**
	 * reepresent the category of identifier
	 */
	private IdentifierCategoryEnum category;

	public IdentifierTypeEnum getType() {
		return type;
	}

	public void setType(IdentifierTypeEnum type) {
		this.type = type;
	}

	public IdentifierCategoryEnum getCategory() {
		return category;
	}

	public void setCategory(IdentifierCategoryEnum category) {
		this.category = category;
	}
}
