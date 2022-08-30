package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CodeQuality;
import com.capitalone.dashboard.model.LibraryPolicyResult;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for {@link CodeQuality} data.
 */
public interface LibraryPolicyResultsRepository extends CrudRepository<LibraryPolicyResult, ObjectId>, QuerydslPredicateExecutor<LibraryPolicyResult> {


    LibraryPolicyResult findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);
    LibraryPolicyResult findByCollectorItemIdAndEvaluationTimestamp(ObjectId collectorItemId, long evaluationTimestamp);
    LibraryPolicyResult findByCollectorItemId(ObjectId collectorItemId);
    List<LibraryPolicyResult> findByCollectorItemIdAndTimestampIsBetweenOrderByTimestampDesc(ObjectId collectorItemId, long beginDate, long endDate);
    List<LibraryPolicyResult> findByCollectorItemIdAndEvaluationTimestampIsBetweenOrderByTimestampDesc(ObjectId collectorItemId, long beginDate, long endDate);
    LibraryPolicyResult findTopByCollectorItemIdOrderByTimestampDesc(ObjectId collectorItemId);
    LibraryPolicyResult findTopByCollectorItemIdOrderByEvaluationTimestampDesc(ObjectId collectorItemId);
}
