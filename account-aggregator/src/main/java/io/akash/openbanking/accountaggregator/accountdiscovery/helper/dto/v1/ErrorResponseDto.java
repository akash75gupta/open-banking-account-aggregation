package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;

import java.util.List;

public class ErrorResponseDto {
	private String timestamp;
	private List<String> message;

	public ErrorResponseDto(String timestamp, List<String> message, String details) {
		this.timestamp = timestamp;
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public List<String> getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorResponseDto [timestamp=" + timestamp + ", message=" + message + "]";
	}
}