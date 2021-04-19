package com.capitalone.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;

/**
 * A {@link Collector} repository
 */
public interface CollectorRepository extends BaseCollectorRepository<Collector> {

    Optional<Collector> findById(ObjectId id);
    List<Collector> findAllByCollectorType(CollectorType collectorType);
}
