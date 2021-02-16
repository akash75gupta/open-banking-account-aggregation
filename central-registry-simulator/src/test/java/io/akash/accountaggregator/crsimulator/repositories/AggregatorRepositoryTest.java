package io.akash.accountaggregator.crsimulator.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.accountaggregator.crsimulator.model.Aggregator;

public interface AggregatorRepositoryTest extends MongoRepository<Aggregator, String>{
	public Optional<Aggregator> findById(String id);

}
