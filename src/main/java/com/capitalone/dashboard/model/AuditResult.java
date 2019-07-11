package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "audit_results")
public class AuditResult extends BaseModel {

    private ObjectId dashboardId;
    private String dashboardTitle;
    private String lineOfBusiness;
    private String configItemBusServName;
    private String configItemBusAppName;
    private String configItemBusServOwner;
    private String configItemBusAppOwner;
    private ObjectId collectorItemId;
    private AuditType auditType;
    private String auditTypeStatus;
    private String auditStatus;
    private String url;
    private String auditDetails;
    private Map traceability;
    private long timestamp;
    private Map<String, Object> options = new HashMap<>();

    public AuditResult() { }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public AuditResult(ObjectId dashboardId, String dashboardTitle, String lineOfBusiness, String configItemBusServName,
                       String configItemBusAppName, String configItemBusServOwner, String configItemBusAppOwner, AuditType auditType,
                       String auditTypeStatus, String auditStatus, String auditDetails, String url, Map traceability, long timestamp) {
        this.dashboardId = dashboardId;
        this.dashboardTitle = dashboardTitle;
        this.lineOfBusiness = lineOfBusiness;
        this.configItemBusServName = configItemBusServName;
        this.configItemBusAppName = configItemBusAppName;
        this.configItemBusServOwner = configItemBusServOwner;
        this.configItemBusAppOwner = configItemBusAppOwner;
        this.auditType = auditType;
        this.auditTypeStatus = auditTypeStatus;
        this.auditStatus = auditStatus;
        this.url = url;
        this.auditDetails = auditDetails;
        this.traceability = traceability;
        this.timestamp = timestamp;
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public AuditResult(ObjectId dashboardId, String dashboardTitle, String lineOfBusiness, String configItemBusServName,
                       String configItemBusAppName, String configItemBusServOwner, String configItemBusAppOwner, AuditType auditType,
                       String auditTypeStatus, String auditStatus, String auditDetails, String url, long timestamp) {
        this.dashboardId = dashboardId;
        this.dashboardTitle = dashboardTitle;
        this.lineOfBusiness = lineOfBusiness;
        this.configItemBusServName = configItemBusServName;
        this.configItemBusAppName = configItemBusAppName;
        this.configItemBusServOwner = configItemBusServOwner;
        this.configItemBusAppOwner = configItemBusAppOwner;
        this.auditType = auditType;
        this.auditTypeStatus = auditTypeStatus;
        this.auditStatus = auditStatus;
        this.url = url;
        this.auditDetails = auditDetails;
        this.timestamp = timestamp;
    }

    public ObjectId getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(ObjectId dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardTitle() {
        return dashboardTitle;
    }

    public void setDashboardTitle(String dashboardTitle) {
        this.dashboardTitle = dashboardTitle;
    }

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public String getConfigItemBusServName() {
        return configItemBusServName;
    }

    public void setConfigItemBusServName(String configItemBusServName) {
        this.configItemBusServName = configItemBusServName;
    }

    public String getConfigItemBusAppName() {
        return configItemBusAppName;
    }

    public void setConfigItemBusAppName(String configItemBusAppName) {
        this.configItemBusAppName = configItemBusAppName;
    }

    public String getConfigItemBusServOwner() {
        return configItemBusServOwner;
    }

    public void setConfigItemBusServOwner(String configItemBusServOwner) {
        this.configItemBusServOwner = configItemBusServOwner;
    }

    public String getConfigItemBusAppOwner() {
        return configItemBusAppOwner;
    }

    public void setConfigItemBusAppOwner(String configItemBusAppOwner) {
        this.configItemBusAppOwner = configItemBusAppOwner;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public String getAuditTypeStatus() {
        return auditTypeStatus;
    }

    public void setAuditTypeStatus(String auditTypeStatus) {
        this.auditTypeStatus = auditTypeStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public Map getTraceability() { return traceability; }

    public void setTraceability(Map traceability) { this.traceability = traceability; }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}