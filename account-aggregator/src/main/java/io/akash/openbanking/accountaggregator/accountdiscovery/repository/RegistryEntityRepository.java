package io.akash.openbanking.accountaggregator.accountdiscovery.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;

/** 
 * This repository is used for query in user collection in mongoDB.
 * 
 * @author Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-03-27
 * 
 **/

public interface RegistryEntityRepository extends MongoRepository<RegistryEntity,String> {
   RegistryEntity findByBusinessId(String businessId);
}


