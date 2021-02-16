package io.akash.accountaggregator.fipsimulator.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.accountaggregator.fipsimulator.model.entities.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	Customer findByPhone(String phone);
	Customer findByEmail(String email);
	Customer findByAadhar(String aadhar);
	Customer findByPan(String pan);
	Customer findByCrn(String crn);
}
