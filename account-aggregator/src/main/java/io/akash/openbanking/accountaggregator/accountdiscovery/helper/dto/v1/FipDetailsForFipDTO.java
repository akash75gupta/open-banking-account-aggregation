package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

/**
 * Purpose: This class  holds FIP details.
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class  FipDetailsForFipDTO {
    /**
     * Name of the requested FIP
     */
    private String name;

    /**
     * Identifier of the requested FIP
     */
    private String id;

    /**
     * Contains a list of the requested financial information type
     */
    private List<FITypeDTO>  fiTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FITypeDTO> getFiTypes() {
        return fiTypes;
    }

    public void setFiTypes(List<FITypeDTO> fiTypes) {
        this.fiTypes = fiTypes;
    }
}
