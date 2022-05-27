package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.InfrastructureScan;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for {@link InfrastructureScan} data.
 */
public interface InfrastructureScanRepository extends CrudRepository<InfrastructureScan, ObjectId> {


    InfrastructureScan findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);
    List<InfrastructureScan> findByCollectorItemId(ObjectId collectorItemId);
    List<InfrastructureScan> findByCollectorItemIdOrderByTimestampDesc(ObjectId collectorItemId);
    List<InfrastructureScan> findByCollectorItemIdAndTimestampIsBetweenOrderByTimestampDesc(ObjectId collectorItemId, long beginDate, long endDate);
    InfrastructureScan findTopByCollectorItemIdOrderByTimestampDesc(ObjectId collectorItemId);
    InfrastructureScan findByBusinessServiceAndInstanceId(String businessService, String instanceId);
    List<InfrastructureScan> findAllByBusinessServiceOrderByTimestampDesc(String businessService);
    List<InfrastructureScan> findAllByBusinessServiceAndBusinessApplicationOrderByTimestampDesc(String businessService, String businessApplication);
    InfrastructureScan findTopByBusinessServiceOrderByTimestampDesc(String businessService);
    InfrastructureScan findTopByBusinessServiceAndBusinessApplicationOrderByTimestampDesc(String businessService, String businessApplication);
    void deleteAllByCollectorItemId(ObjectId collectorItemId);
}
