package io.akash.accountaggregator.fipsimulator.model.dtos;

import java.util.List;

import io.akash.accountaggregator.fipsimulator.model.enums.AuthenticatorType;

public class AccountDiscoveryResponseDTO extends AAResponseDTOBase {
	private AuthenticatorType authenticatorType;
	private List<AccountDTO> discoveredAccounts;
	
	public AuthenticatorType getAuthenticatorType() {
		return authenticatorType;
	}
	public void setAuthenticatorType(AuthenticatorType authenticatorType) {
		this.authenticatorType = authenticatorType;
	}
	public List<AccountDTO> getDiscoveredAccounts() {
		return discoveredAccounts;
	}
	public void setDiscoveredAccounts(List<AccountDTO> discoveredAccounts) {
		this.discoveredAccounts = discoveredAccounts;
	}
}
