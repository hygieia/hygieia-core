package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.AuditReport;
import com.capitalone.dashboard.model.AuditType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuditReportRepository extends CrudRepository<AuditReport, ObjectId>  {
    AuditReport findTop1ByBusinessApplicationAndBusinessServiceAndAuditTypeAndIdentifierNameAndIdentifierVersionAndIdentifierUrlOrderByTimestampDesc(String businessApplication, String businessService, AuditType auditType, String identifierName, String identifierVersion, String identifierUrl);
    AuditReport findTop1ByAuditTypeAndIdentifierNameAndIdentifierVersionAndIdentifierUrlOrderByTimestampDesc(AuditType auditType, String identifierName, String identifierVersion, String identifierUrl);
    List<AuditReport> findByBusinessApplicationAndBusinessServiceAndAuditTypeAndIdentifierNameAndIdentifierVersionAndIdentifierUrl(AuditType auditType, String identifierName, String identifierVersion, String identifierUrl);
    List<AuditReport> findByAuditTypeAndIdentifierNameAndIdentifierVersionAndIdentifierUrl(AuditType auditType, String identifierName, String identifierVersion, String identifierUrl);
}
