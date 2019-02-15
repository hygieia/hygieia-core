package com.capitalone.dashboard.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.FortifyScanReport;

public interface FortifyScanRepository extends CrudRepository<FortifyScanReport, ObjectId>{
	
	FortifyScanReport findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);

	List<FortifyScanReport> findByCollectorItemId(ObjectId id);
}
