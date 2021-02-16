package io.akash.accountaggregator.fipsimulator.model.entities;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class Details {
	
	@Field("type")
    private String type;
	   
	@Field("description")
    private String description;

    @Field("posted")
    private Date posted;

    @Field("completed")
    private Date completed;

    @Field("new_balance")
    private Amount newBalance;

    @Field("value")
    private Amount value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Amount getValue() {
		return value;
	}

	public void setValue(Amount value) {
		this.value = value;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public Date getCompleted() {
		return completed;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public Amount getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(Amount newBalance) {
		this.newBalance = newBalance;
	}
}