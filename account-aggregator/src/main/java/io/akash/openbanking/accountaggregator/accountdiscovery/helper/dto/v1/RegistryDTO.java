package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;

/**
 * Purpose:- 
 * Holds registry information temporaily during exchanges with the central registry
 *
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-10 
 */

public class RegistryDTO {
    /**
     * Represents the list of statkeholder entities registered with the central repository
     */
    private List<RegistryEntity> entities;

    public List<RegistryEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<RegistryEntity> entities) {
        this.entities = entities;
    }
}
