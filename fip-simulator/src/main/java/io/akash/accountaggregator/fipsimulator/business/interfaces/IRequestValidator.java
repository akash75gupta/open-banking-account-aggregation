package io.akash.accountaggregator.fipsimulator.business.interfaces;

import io.akash.accountaggregator.fipsimulator.model.dtos.RequestBaseDTO;

public interface IRequestValidator {
	public Boolean validate(RequestBaseDTO request);
}
