package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.AuthenticatorTypeEnum;

/**
 * Purpose: This class mapped the response getting from FIP.This class used for holding discovered account for customer
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryFipResponseDTO {

    /**
     * Specify the information for account discovery process
     */
    private String ver;
    /**
     * Represent the timestamp
     */
    private String timestamp;

    /**
     * An unique transaction identifier used for providing an end to end traceability.
     */
    private String txnid;

    /**
     * represent  type of authenticator used for delivering OTP
     */
    private AuthenticatorTypeEnum authenticatorType;

    /**
     * represent A list of discoverd accounts
     */
    private List<DiscoveredAccountFipDTO> discoveredAccounts;

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

    public AuthenticatorTypeEnum getAuthenticatorType() {
        return authenticatorType;
    }

    public void setAuthenticatorType(AuthenticatorTypeEnum authenticatorType) {
        this.authenticatorType = authenticatorType;
    }

    public List<DiscoveredAccountFipDTO> getDiscoveredAccounts() {
        return discoveredAccounts;
    }

    public void setDiscoveredAccounts(List<DiscoveredAccountFipDTO> discoveredAccounts) {
        this.discoveredAccounts = discoveredAccounts;
    }
}
