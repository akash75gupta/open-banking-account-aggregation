package io.akash.openbanking.accountaggregator.accountdiscovery.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.RegistryEntityApiDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.RegistryEntityKeyDTO;

import java.util.List;

/**
 * Purpose:- 
 * This is a model class that stores information pertaining to an entity fetched from the Central Registry
 * 
 *
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02 
 */


@Component
@Document(collection = "registry")
public class RegistryEntity {
	
	private String type;

	/**
	 * represent the Mongo DB object id of fip
	 **/
	
	@Id
	private ObjectId id;

	/**
	 * represent the id of fip
	 */
	
	private String businessId;
	
	/**
	 * represent the name of fip
	 */
	private String name;

	/**
	 * represent the registered phone numbers of fip
	 */
	private List<String> phones;

	/**
	 * represent the registered email of fip
	 */
	private List<String> emails;

	/**
	 *  * represent the list of identifiers  
	 */
	private List<String> identifiers;

	/**
	 * represent set of apis
	 */
	private List<RegistryEntityApiDTO> apis;

	/**
	 * represent the address of doc
	 */
	private List<String> docs;

	/**
	 * represent set of the keys for fip
	 */
	private List<RegistryEntityKeyDTO> keys;

	/**
	 * Represent the url of logo
	 */
	private String logoUrl;

	public RegistryEntity() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public List<RegistryEntityApiDTO> getApis() {
		return apis;
	}

	public void setApis(List<RegistryEntityApiDTO> apis) {
		this.apis = apis;
	}

	public List<String> getDocs() {
		return docs;
	}

	public void setDocs(List<String> docs) {
		this.docs = docs;
	}

	public List<RegistryEntityKeyDTO> getKeys() {
		return keys;
	}

	public void setKeys(List<RegistryEntityKeyDTO> keys) {
		this.keys = keys;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public List<String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	@Override
	public String toString() {
		return "RegistryEntity [type=" + type + ", id=" + id + ", businessId=" + businessId + ", name=" + name
				+ ", phones=" + phones + ", emails=" + emails + ", identifiers=" + identifiers + ", apis=" + apis
				+ ", docs=" + docs + ", keys=" + keys + ", logoUrl=" + logoUrl + "]";
	}
}
