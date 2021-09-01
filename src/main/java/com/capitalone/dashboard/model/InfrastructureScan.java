package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "infrastructure_scan")
public class InfrastructureScan extends BaseModel{
    private ObjectId collectorItemId;
    private String businessService;
    private String businessApplication;
    private String instanceId;
    private String ipAddress;
    private String createdTimeStamp;
    private List<Labels> labels;
    private RepositoryDigest repositoryDigest;
    private String ownerDept;
    private String ownerSubDept;
    private String developmentOwner;
    private String ownerEmailAddress;
    private String applicationOwnerFullName;
    private List<Vulnerability> vulnerabilities;
    private int limit;
    private String nextRecordKey;
    private long timestamp;
    private ObjectId buildId;
    private String buildUrl;

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public String getBusinessService() {
        return businessService;
    }

    public void setBusinessService(String businessService) {
        this.businessService = businessService;
    }

    public String getBusinessApplication() {
        return businessApplication;
    }

    public void setBusinessApplication(String businessApplication) {
        this.businessApplication = businessApplication;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public void setLabels(List<Labels> labels) {
        this.labels = labels;
    }

    public RepositoryDigest getRepositoryDigest() {
        return repositoryDigest;
    }

    public void setRepositoryDigest(RepositoryDigest repositoryDigest) {
        this.repositoryDigest = repositoryDigest;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public String getOwnerSubDept() {
        return ownerSubDept;
    }

    public void setOwnerSubDept(String ownerSubDept) {
        this.ownerSubDept = ownerSubDept;
    }

    public String getDevelopmentOwner() {
        return developmentOwner;
    }

    public void setDevelopmentOwner(String developmentOwner) {
        this.developmentOwner = developmentOwner;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(String ownerEmailAddress) {
        this.ownerEmailAddress = ownerEmailAddress;
    }

    public String getApplicationOwnerFullName() {
        return applicationOwnerFullName;
    }

    public void setApplicationOwnerFullName(String applicationOwnerFullName) {
        this.applicationOwnerFullName = applicationOwnerFullName;
    }

    public List<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<Vulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNextRecordKey() {
        return nextRecordKey;
    }

    public void setNextRecordKey(String nextRecordKey) {
        this.nextRecordKey = nextRecordKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ObjectId getBuildId() {
        return buildId;
    }

    public void setBuildId(ObjectId buildId) {
        this.buildId = buildId;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }
}
