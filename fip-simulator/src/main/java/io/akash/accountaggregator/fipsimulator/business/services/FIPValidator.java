package io.akash.accountaggregator.fipsimulator.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.akash.accountaggregator.fipsimulator.business.interfaces.IFIPValidator;
import io.akash.accountaggregator.fipsimulator.exception.exceptions.CustomException;
import io.akash.accountaggregator.fipsimulator.model.dtos.FIPDetailsDTO;

@Service
public class FIPValidator implements IFIPValidator{
	@Autowired
	Environment env;
	
	@Override
	public Boolean areFIPDetailsValid(FIPDetailsDTO fipDetails) {
		String fipId = fipDetails.getId();
		String fipName = fipDetails.getName();
		
		if(!fipId.equals(env.getProperty("fip.id"))) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Fip Id!");
		}
	
		if(!fipName.equals(env.getProperty("fip.name"))) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Fip Name!");
		}
		
		return true;
	}
}
