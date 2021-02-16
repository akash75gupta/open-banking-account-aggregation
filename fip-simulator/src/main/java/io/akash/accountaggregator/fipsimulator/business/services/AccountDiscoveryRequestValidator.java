package io.akash.accountaggregator.fipsimulator.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.akash.accountaggregator.fipsimulator.business.interfaces.IRequestValidator;
import io.akash.accountaggregator.fipsimulator.exception.exceptions.CustomException;
import io.akash.accountaggregator.fipsimulator.model.dtos.AccountDiscoveryRequestDTO;
import io.akash.accountaggregator.fipsimulator.model.dtos.RequestBaseDTO;

@Service
public class AccountDiscoveryRequestValidator implements IRequestValidator{
	@Autowired
	private CustomerValidator customerValidator;
	
	@Autowired
	private FIPValidator fipValidator;

	@Override
	public Boolean validate(RequestBaseDTO request){
		Boolean isRequestValid = false;
		
		AccountDiscoveryRequestDTO discoveryRequest;
		
		if(request instanceof AccountDiscoveryRequestDTO) {
			discoveryRequest = (AccountDiscoveryRequestDTO)request;
		}else {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Expecting AccountDiscoveryRequestDTO.class but received an instance of:"+request.getClass());
		}
		
		Boolean isCustomerValid = customerValidator.isCustomerValid(discoveryRequest.getCustomer());	
	
		Boolean areIdentifiersValid = customerValidator.areIdentifiersValid(discoveryRequest.getIdentifiers());
		
		Boolean areFipDetailsValid = fipValidator.areFIPDetailsValid(discoveryRequest.getFipDetails());
		
		isRequestValid = isCustomerValid && areIdentifiersValid && areFipDetailsValid;
		
		return isRequestValid;
	}
	
	
	
}
