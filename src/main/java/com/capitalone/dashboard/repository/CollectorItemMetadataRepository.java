package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CollectorItemMetadata;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CollectorItemMetadataRepository extends CrudRepository<CollectorItemMetadata, ObjectId>, QueryDslPredicateExecutor<CollectorItemMetadata> {

    @Query(value = "{ 'collectorId' : ?0, 'collectorItemId' : ?1}")
    CollectorItemMetadata findDistinctTopByCollectorIdAndCollectorItemId(ObjectId collectorId, ObjectId collectorItemId);

    @Query(value = "{ 'collectorItemId' : ?0}")
    CollectorItemMetadata findDistinctTopByCollectorItemId(Object collectorItemId);

    @Query(value = "{ 'type' : ?0, 'collectorId' : ?1}")
    List<CollectorItemMetadata> findAllByCollectorTypeAndCollectorId(String type, Object collectorId);


}
