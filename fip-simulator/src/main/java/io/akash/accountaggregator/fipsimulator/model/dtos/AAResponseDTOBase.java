package io.akash.accountaggregator.fipsimulator.model.dtos;

public abstract class AAResponseDTOBase {
	private String ver;
    private String timestamp;
    private String txnid;
    
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
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
}
