package io.akash.accountaggregator.fipsimulator.model.dtos;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountDiscoveryRequestDTO extends RequestBaseDTO {
	
	@NotNull(message = "customer field is manadatory and cannot be null")
	private CustomerDTO customer;
	
	@NotNull(message = "identifiers field is manadatory and cannot be null")
	@NotEmpty(message = "identifiers field is manadatory and cannot be empty")
	private List<IdentifierDTO> identifiers;
	
	@NotNull(message = "fipDetails field is manadatory and cannot be null")
	private FIPDetailsDTO fipDetails;
	
	@NotNull(message = "meta field is manadatory and cannot be null")
	private MetaInfoDTO meta;
	
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	public List<IdentifierDTO> getIdentifiers() {
		return identifiers;
	}
	public void setIdentifiers(List<IdentifierDTO> identifiers) {
		this.identifiers = identifiers;
	}
	public FIPDetailsDTO getFipDetails() {
		return fipDetails;
	}
	public void setFipDetails(FIPDetailsDTO fipDetails) {
		this.fipDetails = fipDetails;
	}
	public MetaInfoDTO getMeta() {
		return meta;
	}
	public void setMeta(MetaInfoDTO meta) {
		this.meta = meta;
	}
	@Override
	public String toString() {
		return "AccountDiscoveryRequestDTO [customer=" + customer + ", identifiers=" + identifiers + ", fipDetails="
				+ fipDetails + ", meta=" + meta + ", getVer()=" + getVer() + ", getTimestamp()=" + getTimestamp()
				+ ", getTxnid()=" + getTransactionId() + "]";
	}
}
