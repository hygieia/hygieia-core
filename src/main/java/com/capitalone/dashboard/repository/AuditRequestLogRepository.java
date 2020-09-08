package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.AuditRequestLog;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface AuditRequestLogRepository extends CrudRepository<AuditRequestLog, ObjectId> {

}
