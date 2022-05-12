package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.MaturityModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaturityModelRepository extends CrudRepository<MaturityModel, ObjectId>, QuerydslPredicateExecutor<MaturityModel> {
    MaturityModel findByProfile(String profile);

    @Query(value="{}", fields="{ profile : 1 }")
    List<MaturityModel> getAllProfiles();
}
