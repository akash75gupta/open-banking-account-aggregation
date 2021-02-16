package io.akash.accountaggregator.fipsimulator.business.interfaces;

import io.akash.accountaggregator.fipsimulator.model.dtos.FIPDetailsDTO;

public interface IFIPValidator {
	public Boolean areFIPDetailsValid(FIPDetailsDTO fipDetails);
}
