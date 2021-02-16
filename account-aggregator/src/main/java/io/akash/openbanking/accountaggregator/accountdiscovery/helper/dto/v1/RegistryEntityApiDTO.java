package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;


/**
 * Purpose: This class  holds APIS details of FIP.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class RegistryEntityApiDTO {

    /**
     * represent the url of fip
     */
    private String url;

    /**
     * represent the version of api
     */
    private String ver;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Override
    public String toString() {
        return "ApisDTO{" +
                "url='" + url + '\'' +
                ", ver='" + ver + '\'' +
                '}';
    }
}
