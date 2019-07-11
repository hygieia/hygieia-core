package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.LogAnalysis;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LogAnalysizerRepository extends CrudRepository<LogAnalysis, ObjectId>, QueryDslPredicateExecutor<LogAnalysis> {
}
