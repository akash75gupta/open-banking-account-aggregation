package io.akash.accountaggregator.fipsimulator.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.akash.accountaggregator.fipsimulator.business.interfaces.ICustomerValidator;
import io.akash.accountaggregator.fipsimulator.data.services.CustomerDataService;
import io.akash.accountaggregator.fipsimulator.exception.exceptions.CustomException;
import io.akash.accountaggregator.fipsimulator.model.dtos.CustomerDTO;
import io.akash.accountaggregator.fipsimulator.model.dtos.IdentifierDTO;
import io.akash.accountaggregator.fipsimulator.model.entities.Customer;
import io.akash.accountaggregator.fipsimulator.model.enums.IdentifierCategory;

@Service
public class CustomerValidator implements ICustomerValidator{

	@Autowired
	CustomerDataService customerDataService;
	
	@Override
	public Boolean isIdentifierValid(IdentifierDTO identifier){
		Customer customer = null;
		
		switch(identifier.getType()) {
			case MOBILE:
				customer = customerDataService.getCustomerByMobile(identifier.getValue());
				break;
			case AADHAR:
				customer = customerDataService.getCustomerByAadhar(identifier.getValue());
				break;
			case PAN:
				customer = customerDataService.getCustomerByPan(identifier.getValue());
				break;
			case EMAIL:
				customer = customerDataService.getCustomerByEmail(identifier.getValue());
				break;
			case CRN:
				customer = customerDataService.getCustomerByCrn(identifier.getValue());
				break;
			default:
				throw new CustomException(HttpStatus.BAD_REQUEST,"Wrong Identifier Type! "+identifier.getType());
		}
		
		if(null != customer) {
			return true;
		}else {
			return false;
		}		
	}
	
	@Override
	public Boolean areIdentifiersValid(List<IdentifierDTO> identifiers) {
		Boolean areIdentifiersValid = false;
		
		for(IdentifierDTO identifier:identifiers) {
				areIdentifiersValid = isIdentifierValid(identifier);
				if(areIdentifiersValid) {
					break;
				}
		}
		
		if(!areIdentifiersValid) {
			throw new CustomException(HttpStatus.BAD_REQUEST,"Invalid Identifiers! "
									+ "No customer exists for the given identifiers");
		}
		
		return areIdentifiersValid;	
	}
	
	
	@Override
	public Boolean isIdentifierStrong(IdentifierDTO identifier) {
		if(identifier.getCategory().equals(IdentifierCategory.STRONG)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean isIdentifierWeak(IdentifierDTO identifier) {
		if(identifier.getCategory().equals(IdentifierCategory.WEAK)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean isIdentifierAncillary(IdentifierDTO identifier) {
		if(identifier.getCategory().equals(IdentifierCategory.ANCILLARY)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Not sure if this validation is necessary. 
	 * Also, the inclusion of customer details in the discovery request is debatable.
	 * Need to re-visit. For now isCustomerValid will return true by default.
	 */
	
	@Override
	public Boolean isCustomerValid(CustomerDTO customer) {
		return true;
	}
}
