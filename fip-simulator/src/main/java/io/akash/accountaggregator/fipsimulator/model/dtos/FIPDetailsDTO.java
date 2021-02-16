package io.akash.accountaggregator.fipsimulator.model.dtos;

import java.util.List;

public class  FIPDetailsDTO {
    private String name;
    private String id;
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
