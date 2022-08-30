package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Pipeline;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PipelineRepository extends CrudRepository<Pipeline, ObjectId>, QuerydslPredicateExecutor<Pipeline> {

    Pipeline findByCollectorItemId(ObjectId collectorItemId);

    List<Pipeline> findByCollectorItemIdIn(List<ObjectId> collectorItemIds);

}
