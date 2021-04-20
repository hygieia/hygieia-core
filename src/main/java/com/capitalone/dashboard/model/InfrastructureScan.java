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
    private String clusterId;
    private String hostName;
    private Map<String, String> containerAttributes = new HashMap<>();
    private String instanceId;
    private String ipAddress;
    private String operatingSystem;
    private String region;
    private String createdTimeStamp;
    private Labels labels;
    private String artifactSha;
    private RepositoryDigest repositoryDigest;
    private String ownerDept;
    private String ownerSubDept;
    private String businessApplicationName;
    private String engineeringLeadId;
    private String ownerEmailAddress;
    private String applicationOwnerFullName;
    private List<Vulnerability> vulnerabilities;
    private int limit;
    private String nextRecordKey;
    private long timestamp;
    private ObjectId buildId;

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

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Map<String, String> getContainerAttributes() {
        return containerAttributes;
    }

    public void setContainerAttributes(Map<String, String> containerAttributes) {
        this.containerAttributes = containerAttributes;
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

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getArtifactSha() {
        return artifactSha;
    }

    public void setArtifactSha(String artifactSha) {
        this.artifactSha = artifactSha;
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

    public String getBusinessApplicationName() {
        return businessApplicationName;
    }

    public void setBusinessApplicationName(String businessApplicationName) {
        this.businessApplicationName = businessApplicationName;
    }

    public String getEngineeringLeadId() {
        return engineeringLeadId;
    }

    public void setEngineeringLeadId(String engineeringLeadId) {
        this.engineeringLeadId = engineeringLeadId;
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
}
