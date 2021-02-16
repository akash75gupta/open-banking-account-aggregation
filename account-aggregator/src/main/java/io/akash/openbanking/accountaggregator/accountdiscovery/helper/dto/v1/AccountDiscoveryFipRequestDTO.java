package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

/**
 * Purpose: This class is uesd for sending request for discovery of customer accounts in FIP.
 * @author , Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-03-27
 */
public class AccountDiscoveryFipRequestDTO{

    /**
     * Version of the API
     */
    private String ver;
    /**
     * Creation timestamp of the message which will be updated at each call
     */
    private String timestamp;

    /**
     * Creation timestamp of the message which will be updated at each leg
     */
    private String txnid;

    /**
     * Contains the information about the customer
     */
    private AccountDiscoveryFipRequestCustomerDTO customer;

    /**
     *
     * A set of Identifiers for discovering the accounts of a customer
     */
    private List<AccountDiscoveryFipRequestIdentifierDTO> identifiers;

    /**
     *
     * Details of a financial information provider
     */
    private FipDetailsForFipDTO fipDetails;

    /**
     *
     * Addtional Meta information passes to the FIP
     */
    private AccountDiscoveryFipRequestMetaDTO meta;

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

    public AccountDiscoveryFipRequestCustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(AccountDiscoveryFipRequestCustomerDTO customer) {
        this.customer = customer;
    }

    public List<AccountDiscoveryFipRequestIdentifierDTO> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<AccountDiscoveryFipRequestIdentifierDTO> identifiers) {
        this.identifiers = identifiers;
    }

    public FipDetailsForFipDTO getFipDetails() {
        return fipDetails;
    }

    public void setFipDetails(FipDetailsForFipDTO fipDetails) {
        this.fipDetails = fipDetails;
    }

    public AccountDiscoveryFipRequestMetaDTO getMeta() {
        return meta;
    }

    public void setMeta(AccountDiscoveryFipRequestMetaDTO meta) {
        this.meta = meta;
    }
}
