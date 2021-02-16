package io.akash.accountaggregator.fipsimulator.business.interfaces;

import java.util.List;

import io.akash.accountaggregator.fipsimulator.model.dtos.CustomerDTO;
import io.akash.accountaggregator.fipsimulator.model.dtos.IdentifierDTO;

public interface ICustomerValidator {
	public Boolean isCustomerValid(CustomerDTO id);
	public Boolean isIdentifierValid(IdentifierDTO identifier);
	public Boolean areIdentifiersValid(List<IdentifierDTO> identifiers);
	public Boolean isIdentifierStrong(IdentifierDTO identifier);
	public Boolean isIdentifierWeak(IdentifierDTO identifier);
	public Boolean isIdentifierAncillary(IdentifierDTO identifier);
}
