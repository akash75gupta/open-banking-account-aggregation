package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

/**
 * Purpose: 
 * This class  holds necessary  details about FIP required by AA client for showing data to user .
 * 
 * @author Akash Gupta (akash75gupta@gmail.com)
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class FIPRegistryAppResponseDTO {

    /**
     * represent the id of fip
     */
    private String id;

    /**
     * represent the name of fip
     */
    private String name;

    /**
     * represent the logo of fip
     */
    private  String logoUrl;
    
    private List<String> identifiers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

	public List<String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	@Override
	public String toString() {
		return "FipRequredDataToSendDTO [id=" + id + ", name=" + name + ", logoUrl=" + logoUrl + ", identifiers="
				+ identifiers + "]";
	}
}
