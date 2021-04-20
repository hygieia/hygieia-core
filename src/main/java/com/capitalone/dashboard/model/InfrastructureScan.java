package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "infrastructure_scan")
public class InfrastructureScan extends BaseModel{
    private ObjectId collectorItemId;
    private String businessService;
    private String businessApplication;
    private String accountId;
    private String hostName;
    private String instanceId;
    private String ipAddress;
    private String operatingSystem;
    private String region;
    private  String imageId;
    private String containerId;
    private String createDTimeStamp;
    private  String containerName;
    private Labels labels; // create object
    private String artifactorySha;
    private RepositoryDigest repositoryDigest; // create Object
    private String technologyDivision;
    private String technologySubdivision;
    private String asvName;
    private String businessApplicationName;
    private String engineeringLeadEid;
    private String ownerEmailAddress;
    private String applicationOwnerFullName;
    private List<Vulnerability> vulnerabilities; //create object
    private int limit;
    private String nextRecordKey;
    private long timestamp;
    private ObjectId buildId;

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

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getCreateDTimeStamp() {
        return createDTimeStamp;
    }

    public void setCreateDTimeStamp(String createDTimeStamp) {
        this.createDTimeStamp = createDTimeStamp;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getArtifactorySha() {
        return artifactorySha;
    }

    public void setArtifactorySha(String artifactorySha) {
        this.artifactorySha = artifactorySha;
    }

    public RepositoryDigest getRepositoryDigest() {
        return repositoryDigest;
    }

    public void setRepositoryDigest(RepositoryDigest repositoryDigest) {
        this.repositoryDigest = repositoryDigest;
    }

    public String getTechnologyDivision() {
        return technologyDivision;
    }

    public void setTechnologyDivision(String technologyDivision) {
        this.technologyDivision = technologyDivision;
    }

    public String getTechnologySubdivision() {
        return technologySubdivision;
    }

    public void setTechnologySubdivision(String technologySubdivision) {
        this.technologySubdivision = technologySubdivision;
    }

    public String getAsvName() {
        return asvName;
    }

    public void setAsvName(String asvName) {
        this.asvName = asvName;
    }

    public String getBusinessApplicationName() {
        return businessApplicationName;
    }

    public void setBusinessApplicationName(String businessApplicationName) {
        this.businessApplicationName = businessApplicationName;
    }

    public String getEngineeringLeadEid() {
        return engineeringLeadEid;
    }

    public void setEngineeringLeadEid(String engineeringLeadEid) {
        this.engineeringLeadEid = engineeringLeadEid;
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
