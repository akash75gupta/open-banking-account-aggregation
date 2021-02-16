package io.akash.accountaggregator.crsimulator.model.dtos;

import lombok.Data;

@Data
public class ApiDTO {
	private String url;
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
}
