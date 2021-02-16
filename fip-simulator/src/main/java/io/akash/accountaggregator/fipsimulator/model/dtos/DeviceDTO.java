package io.akash.accountaggregator.fipsimulator.model.dtos;

import java.util.List;

public class DeviceDTO {
	private List<TagDTO> tags;

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}
}
