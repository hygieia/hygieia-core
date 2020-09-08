package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.LibraryPolicyReference;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for {@link LibraryPolicyReference} data.
 */
public interface LibraryReferenceRepository extends CrudRepository<LibraryPolicyReference, ObjectId>, QueryDslPredicateExecutor<LibraryPolicyReference> {


    LibraryPolicyReference findByLibraryNameAndOrgName(String name, String orgName);
}
