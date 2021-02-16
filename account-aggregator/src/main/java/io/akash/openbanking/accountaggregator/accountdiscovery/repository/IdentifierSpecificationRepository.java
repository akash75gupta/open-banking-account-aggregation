package io.akash.openbanking.accountaggregator.accountdiscovery.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.openbanking.accountaggregator.accountdiscovery.model.IdentifierSpecification;

/**
 * This repository is used for query  identifiers category i.e, 
 * weather the given identifier type belongs to WEAK or STRONG category.
 * 
 * @author Akash Gupta(akash75gupta@gmail.com)
 * @author 
 * @version 1.0
 * @since   2019-03-27
 * 
 **/

public interface IdentifierSpecificationRepository extends MongoRepository<IdentifierSpecification,String> {
}
