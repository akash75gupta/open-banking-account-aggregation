package io.akash.accountaggregator.fipsimulator.model.dtos;

import io.akash.accountaggregator.fipsimulator.model.enums.FITypeName;

public class FITypeDTO {
    private FITypeName name;
    private String value;

    public FITypeDTO(){}
    
    public FITypeDTO(FITypeName name,String value){
    	this.name = name;
    	this.value = value;
    }
    
  
    public FITypeName getName() {
		return name;
	}

	public void setName(FITypeName name) {
		this.name = name;
	}

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
