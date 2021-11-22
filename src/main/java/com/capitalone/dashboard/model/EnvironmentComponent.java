package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents deployable units (components) deployed to an environment.
 */
@Document(collection = "environment_components")
public class EnvironmentComponent extends BaseModel {
    /**
     * Deploy collectorItemId
     */
    private ObjectId collectorItemId;
    private String environmentID;
    private String environmentName;
    private String environmentUrl;
    private String componentID;
	private String componentName;
    private String componentVersion;
    private String componentPath;
    private String jobUrl;
    private boolean deployed;
    private long deployTime;
    private long asOfDate;
    private ObjectId buildId;
    private String jobStageName;
    private String jobStageStatus;
    private String applicationName;
    private String serviceName;
    private String changeReference;
    private String scmIdentifier;
    private String scmRepo;


	
    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }
    
    public String getEnvironmentID() {
    	return environmentID;
    }
    
    public void setEnvironmentID(String environmentID) {
    	this.environmentID = environmentID;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentUrl() {
        return environmentUrl;
    }

    public void setEnvironmentUrl(String environmentUrl) {
        this.environmentUrl = environmentUrl;
    }

    
    public String getComponentID() {
		return componentID;
	}

	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}
	
    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(String componentVersion) {
        this.componentVersion = componentVersion;
    }

    public String getJobUrl() {
		return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public long getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(long asOfDate) {
        this.asOfDate = asOfDate;
    }

    public long getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(long deployTime) {
        this.deployTime = deployTime;
    }

    public ObjectId getBuildId() { return buildId; }

    public void setBuildId(ObjectId buildId) { this.buildId = buildId; }

    public String getJobStageName() { return jobStageName; }

    public void setJobStageName(String jobStageName) { this.jobStageName = jobStageName; }

    public String getJobStageStatus() { return jobStageStatus; }

    public void setJobStageStatus(String jobStageStatus) { this.jobStageStatus = jobStageStatus; }

    public String getComponentPath() { return componentPath; }

    public void setComponentPath(String componentPath) { this.componentPath = componentPath; }

    public String getApplicationName() { return applicationName; }

    public void setApplicationName(String applicationName) { this.applicationName = applicationName; }

    public String getServiceName() { return serviceName; }

    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getChangeReference() { return changeReference; }

    public void setChangeReference(String changeReference) { this.changeReference = changeReference; }
}
