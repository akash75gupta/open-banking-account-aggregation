package io.akash.accountaggregator.fipsimulator.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.accountaggregator.fipsimulator.model.entities.User;

public interface UserRepository extends MongoRepository<User, String>{
	User findByUsername(String username);
}
