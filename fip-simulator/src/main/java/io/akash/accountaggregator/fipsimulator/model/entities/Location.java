package io.akash.accountaggregator.fipsimulator.model.entities;

public class Location {
  
	private double latitude;
    private double longitude;
    
	public Location() {
		super();
	}

	public Location(double latitude, double longitude) {
		super();
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}

