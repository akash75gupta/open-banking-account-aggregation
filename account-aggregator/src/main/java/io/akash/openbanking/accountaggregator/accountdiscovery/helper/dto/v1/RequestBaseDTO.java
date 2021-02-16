package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class RequestBaseDTO {
	
	@NotNull(message = "customerId field is manadatory and cannot be null")	
	@NotBlank(message = "customerId field is manadatory and cannot be blank")
	private String customerId;
	

	@NotNull(message = "version field is manadatory and cannot be null")	
	@NotBlank(message = "version field is manadatory and cannot be blank")
	private String ver;
	

	@NotNull(message = "timestamp field is manadatory and cannot be null")	
	@NotBlank(message = "timestamp field is manadatory and cannot be blank")
    private String timestamp;
	

	@NotNull(message = "transactionId field is manadatory and cannot be null")	
	@NotBlank(message = "transactionId field is manadatory and cannot be blank")
    private String transactionId;
    
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
