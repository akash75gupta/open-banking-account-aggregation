package io.akash.accountaggregator.fipsimulator.model.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.akash.accountaggregator.fipsimulator.model.enums.AccountDocType;
import io.akash.accountaggregator.fipsimulator.model.enums.AccountType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "accounts")
@TypeAlias("Account")
public class Account {
	@Id
	@Field("_id")
    private String id;
	   
    @Field("label")
    private String label;

    @Field("balance")
    private Amount balance;

	private AccountType type;
	
    @Field("IBAN")
	private String iban;

	@Field("account_routing")
	private AccountRouting accountRouting;
	
    @Field("number")
    private String number;
    
    @Field("owners")
    private List<Owner> owners;
 
    @Field("opening_date")
	private Date openingDate;
    
	private String facility;

	private String odLimit;
	
	private AccountDocType docType;

	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
		this.balance = balance;
	}

	public AccountRouting getAccount_routing() {
		return accountRouting;
	}

	public void setAccount_routing(AccountRouting account_routing) {
		this.accountRouting = account_routing;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getOdLimit() {
		return odLimit;
	}

	public void setOdLimit(String odLimit) {
		this.odLimit = odLimit;
	}

	public AccountDocType getDocType() {
		return docType;
	}

	public void setDocType(AccountDocType docType) {
		this.docType = docType;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
	
	public AccountRouting getAccountRouting() {
		return accountRouting;
	}

	public void setAccountRouting(AccountRouting accountRouting) {
		this.accountRouting = accountRouting;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public List<Owner> getOwners() {
		return owners;
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}
}
	