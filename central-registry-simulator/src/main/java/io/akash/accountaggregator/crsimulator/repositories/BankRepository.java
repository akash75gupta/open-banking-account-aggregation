package io.akash.accountaggregator.crsimulator.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.accountaggregator.crsimulator.model.Bank;

public interface BankRepository extends MongoRepository<Bank, String>{
	public Optional<Bank> findById(String id);

}
