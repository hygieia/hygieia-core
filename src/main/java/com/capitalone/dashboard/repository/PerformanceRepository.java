package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Performance;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PerformanceRepository extends CrudRepository<Performance, ObjectId>, QuerydslPredicateExecutor<Performance> {

    Performance findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);
    List<Performance> findByCollectorItemId (ObjectId collectorItemId);


}
