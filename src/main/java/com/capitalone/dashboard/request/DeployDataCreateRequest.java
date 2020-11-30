package com.capitalone.dashboard.request;

import javax.validation.constraints.NotNull;

public class DeployDataCreateRequest {
    @NotNull
    private String executionId;
    @NotNull
    private String jobUrl;
    @NotNull
    private String appName;
    private String appServiceName;
    @NotNull
    private String envName;
    @NotNull
    private String artifactName;
    @NotNull
    private String artifactVersion;
    private String artifactPath;
    @NotNull
    private String jobName;
    @NotNull
    private String instanceUrl;
    @NotNull
    private String deployStatus;
    @NotNull
    private long startTime;

    private String artifactGroup;
    private String hygieiaId;
    private long endTime;
    private long duration;
    private String startedBy;
    private String collectorName;
    private String niceName;
    private String stageName;
    private String stageStatus;
    private String jobNumber;
    private String buildUrl;

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getArtifactVersion() {
        return artifactVersion;
    }

    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(String deployStatus) {
        this.deployStatus = deployStatus;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getArtifactGroup() {
        return artifactGroup;
    }

    public void setArtifactGroup(String artifactGroup) {
        this.artifactGroup = artifactGroup;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public String getHygieiaId() {
        return hygieiaId;
    }

    public void setHygieiaId(String hygieiaId) {
        this.hygieiaId = hygieiaId;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getStageName() { return stageName; }

    public void setStageName(String stageName) { this.stageName = stageName; }

    public String getStageStatus() { return stageStatus; }

    public void setStageStatus(String stageStatus) { this.stageStatus = stageStatus; }

    public String getJobNumber() { return jobNumber; }

    public void setJobNumber(String jobNumber) { this.jobNumber = jobNumber; }

    public String getAppServiceName() { return appServiceName; }

    public void setAppServiceName(String appServiceName) { this.appServiceName = appServiceName; }

    public String getArtifactPath() { return artifactPath; }

    public void setArtifactPath(String artifactPath) { this.artifactPath = artifactPath; }

    public String getBuildUrl() { return buildUrl; }

    public void setBuildUrl(String buildUrl) { this.buildUrl = buildUrl; }
}
