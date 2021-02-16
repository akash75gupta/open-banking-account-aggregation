package io.akash.accountaggregator.fipsimulator.data.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.accountaggregator.fipsimulator.model.entities.Account;

public interface AccountRepository  extends MongoRepository<Account, String>{
	//Here customer username is the owner id
	public List<Account> findByOwnersId(String customerUsername);
}
