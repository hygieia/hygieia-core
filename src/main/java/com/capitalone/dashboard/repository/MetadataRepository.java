package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Metadata;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface MetadataRepository extends CrudRepository<Metadata, ObjectId>, QuerydslPredicateExecutor<Metadata> {

}
