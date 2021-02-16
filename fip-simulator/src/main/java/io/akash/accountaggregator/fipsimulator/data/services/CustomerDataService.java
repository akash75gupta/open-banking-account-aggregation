package io.akash.accountaggregator.fipsimulator.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.akash.accountaggregator.fipsimulator.data.interfaces.ICustomerDataService;
import io.akash.accountaggregator.fipsimulator.data.repositories.CustomerRepository;
import io.akash.accountaggregator.fipsimulator.exception.exceptions.CustomException;
import io.akash.accountaggregator.fipsimulator.model.entities.Customer;

@Service
public class CustomerDataService implements ICustomerDataService{

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Customer getCustomerByMobile(String mobile){	
		return customerRepository.findByPhone(mobile);
	}
	
	@Override
	public Customer getCustomerById(String id){
		Customer customer = customerRepository.findById(id).get();
		
		if(null == customer) {
			throw new CustomException(HttpStatus.NOT_FOUND,
					"Customer not found for the given id: "+id);
		}
		return customer;
	}

	@Override
	public Customer getCustomerByAadhar(String aadhar) {
	Customer customer = customerRepository.findByAadhar(aadhar);
	
		if(null == customer) {
			throw new CustomException(HttpStatus.NOT_FOUND,
					"Customer not found for the given aadhar number: "+aadhar);
		}
		return customer;
	}

	@Override
	public Customer getCustomerByPan(String pan) {
		Customer customer = customerRepository.findByPan(pan);

		if(null == customer) {
			throw new CustomException(HttpStatus.NOT_FOUND,
					"Customer not found for the given pan number: "+pan);
		}
		return customer;
	}
	
	@Override
	public Customer getCustomerByEmail(String email) {
		Customer customer = customerRepository.findByEmail(email);

		if(null == customer) {
			throw new CustomException(HttpStatus.NOT_FOUND,
					"Customer not found for the given email: "+email);
		}
		return customer;
	}

	public Customer getCustomerByCrn(String crn) {
		Customer customer =  customerRepository.findByCrn(crn);

		if(null == customer) {
			throw new CustomException(HttpStatus.NOT_FOUND,
					"Customer not found for the given crn number: "+crn);
		}
		return customer;
	}
}
