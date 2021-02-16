package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.io.Serializable;
import java.util.List;

/** 
 * Holds information about an FIP pertaining to Account Discovery Request made by the Client APP
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-16
 * 
 **/

public class FipForAppDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4845009376492770453L;
	
	private String id;
    /**
     * represent name of Fip
     */
    private String name;

    /**
     * represent logoUrl
     */
    private String logoUrl;

    /**
     * represent customized discovered account needed for AA client
     */
    private List<DiscoveredAccountForAppDTO> discoveredAccounts;

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

	public List<DiscoveredAccountForAppDTO> getDiscoveredAccounts() {
		return discoveredAccounts;
	}

	public void setDiscoveredAccounts(List<DiscoveredAccountForAppDTO> discoveredAccounts) {
		this.discoveredAccounts = discoveredAccounts;
	}

	@Override
	public String toString() {
		return "FipForAppDTO [id=" + id + ", name=" + name + ", logoUrl=" + logoUrl + ", discoveredAccounts="
				+ discoveredAccounts + "]";
	}
}
