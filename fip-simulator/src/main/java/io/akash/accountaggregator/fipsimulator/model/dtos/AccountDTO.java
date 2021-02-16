package io.akash.accountaggregator.fipsimulator.model.dtos;

import io.akash.accountaggregator.fipsimulator.model.enums.AccountTypeDTO;

public class AccountDTO {
	private FITypeDTO fiType;
	private String accRefNumber;
	private AccountTypeDTO accType;
	private String maskedAccNumber;
	
	public FITypeDTO getFiType() {
		return fiType;
	}
	public void setFiType(FITypeDTO fiType) {
		this.fiType = fiType;
	}
	public String getAccRefNumber() {
		return accRefNumber;
	}
	public void setAccRefNumber(String accRefNumber) {
		this.accRefNumber = accRefNumber;
	}
	
	public String getMaskedAccNumber() {
		return maskedAccNumber;
	}
	public void setMaskedAccNumber(String maskedAccNumber) {
		this.maskedAccNumber = maskedAccNumber;
	}
	public AccountTypeDTO getAccType() {
		return accType;
	}
	public void setAccType(AccountTypeDTO accType) {
		this.accType = accType;
	}
	@Override
	public String toString() {
		return "AccountDTO [fiType=" + fiType + ", accRefNumber=" + accRefNumber + ", accType=" + accType
				+ ", maskedAccNumber=" + maskedAccNumber + "]";
	}	
}
