package io.akash.accountaggregator.fipsimulator.model.dtos;

import javax.validation.constraints.NotNull;

public abstract class RequestBaseDTO {
	@NotNull(message = "ver field is mandatory and cannot be null")
	private String ver;
	@NotNull(message = "timestamp field is mandatory and cannot be null")
    private String timestamp;
	@NotNull(message = "transactionId field is mandatory and cannot be null")
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTxnid(String txnid) {
		this.transactionId = txnid;
	}   
}
