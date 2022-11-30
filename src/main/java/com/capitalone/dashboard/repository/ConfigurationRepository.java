package com.capitalone.dashboard.repository;

import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Configuration;


public interface ConfigurationRepository extends CrudRepository<Configuration, ObjectId> , QuerydslPredicateExecutor<Configuration>{

	Configuration findByCollectorName(String collectorNiceName);
	
}
