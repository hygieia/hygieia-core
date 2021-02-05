package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CollectorItemMetadata;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CollectorItemMetadataRepository extends CrudRepository<CollectorItemMetadata, ObjectId>, QueryDslPredicateExecutor<CollectorItemMetadata> {

}
