package io.akash.accountaggregator.crsimulator.model.dtos;

public enum EntityTypeDTO {
	FIP("FIP"),
	FIU("FIU"),
	AA("AA");
	
	private final String value;
	
	private EntityTypeDTO(String value) {
          this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String value() {
        return this.value;
    }

}
