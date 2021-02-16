package io.akash.accountaggregator.crsimulator.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.accountaggregator.crsimulator.model.NBFC;

public interface NBFCRepositoryTest extends MongoRepository<NBFC, String>{
	public Optional<NBFC> findById(String id);

}
