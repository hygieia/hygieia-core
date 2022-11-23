package com.capitalone.dashboard.repository;

import java.util.List;

import org.bson.types.ObjectId;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;

/**
 * A {@link Collector} repository
 */
public interface CollectorRepository extends BaseCollectorRepository<Collector> {

    List<Collector> findAllById(ObjectId id);
    List<Collector> findAllByCollectorType(CollectorType collectorType);

}
