package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "container_scan")
public class ContainerScan extends BaseModel{
    private ObjectId collectorItemId;
    private String businessService;
    private String businessApplication;
    private String identifierName;
    private String identifierVersion;
    private String identifierUrl;
    private boolean isPromotable;
    private boolean isVulnerable;
    private String httpStatusCode;
    private String errorCode;
    private String message;
    private List<Vulnerability> vulnerabilities;
    private String buildUrl;
    private String imageId;
    private String artifactorySha;
    private String createdTimestamp;
    private String errorDescription;
    private String lastScannedTimestamp;
    private Integer vulnerabilityCount;
    private String applicationServiceName;
    private String lineOfBusiness;
    private String engineeringLeadFullName;
    private String ownerEmailAddress;
    private String technologyDivision;
    private String technologySubdivision;
    private String technologyExecutiveFullName;
    private String businessExecutiveFullName;
    private String businessApplicationName;
    private String environment;
    private String instanceId;
    private String hostName;
    private boolean isLabelCompliant;
    private boolean isGoldenImageCompliant;
    private boolean isRootCompliant;
    private boolean isOverallCompliant;
    private List<Labels> labels;
    private String digest;
    private String repository;
    private String registry;


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

    public boolean isPromotable() {
        return isPromotable;
    }

    public void setPromotable(boolean promotable) {
        isPromotable = promotable;
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<Vulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    public String getIdentifierVersion() {
        return identifierVersion;
    }

    public void setIdentifierVersion(String identifierVersion) {
        this.identifierVersion = identifierVersion;
    }

    public String getIdentifierUrl() {
        return identifierUrl;
    }

    public void setIdentifierUrl(String identifierUrl) {
        this.identifierUrl = identifierUrl;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getArtifactorySha() {
        return artifactorySha;
    }

    public void setArtifactorySha(String artifactorySha) {
        this.artifactorySha = artifactorySha;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getLastScannedTimestamp() {
        return lastScannedTimestamp;
    }

    public void setLastScannedTimestamp(String lastScannedTimestamp) {
        this.lastScannedTimestamp = lastScannedTimestamp;
    }

    public Integer getVulnerabilityCount() {
        return vulnerabilityCount;
    }

    public void setVulnerabilityCount(Integer vulnerabilityCount) {
        this.vulnerabilityCount = vulnerabilityCount;
    }

    public String getApplicationServiceName() {
        return applicationServiceName;
    }

    public void setApplicationServiceName(String applicationServiceName) {
        this.applicationServiceName = applicationServiceName;
    }

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public String getEngineeringLeadFullName() {
        return engineeringLeadFullName;
    }

    public void setEngineeringLeadFullName(String engineeringLeadFullName) {
        this.engineeringLeadFullName = engineeringLeadFullName;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(String ownerEmailAddress) {
        this.ownerEmailAddress = ownerEmailAddress;
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

    public String getTechnologyExecutiveFullName() {
        return technologyExecutiveFullName;
    }

    public void setTechnologyExecutiveFullName(String technologyExecutiveFullName) {
        this.technologyExecutiveFullName = technologyExecutiveFullName;
    }

    public String getBusinessExecutiveFullName() {
        return businessExecutiveFullName;
    }

    public void setBusinessExecutiveFullName(String businessExecutiveFullName) {
        this.businessExecutiveFullName = businessExecutiveFullName;
    }

    public String getBusinessApplicationName() {
        return businessApplicationName;
    }

    public void setBusinessApplicationName(String businessApplicationName) {
        this.businessApplicationName = businessApplicationName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isLabelCompliant() {
        return isLabelCompliant;
    }

    public void setLabelCompliant(boolean labelCompliant) {
        isLabelCompliant = labelCompliant;
    }

    public boolean isGoldenImageCompliant() {
        return isGoldenImageCompliant;
    }

    public void setGoldenImageCompliant(boolean goldenImageCompliant) {
        isGoldenImageCompliant = goldenImageCompliant;
    }

    public boolean isRootCompliant() {
        return isRootCompliant;
    }

    public void setRootCompliant(boolean rootCompliant) {
        isRootCompliant = rootCompliant;
    }

    public boolean isOverallCompliant() {
        return isOverallCompliant;
    }

    public void setOverallCompliant(boolean overallCompliant) {
        isOverallCompliant = overallCompliant;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public void setLabels(List<Labels> labels) {
        this.labels = labels;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }
}
