package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents code quality at a specific point in time. This could include
 * a unit test run, a security scan, static analysis, functional tests,
 * manual acceptance tests or bug reports.
 *
 * Possible Collectors:
 *  Sonar (in scope)
 *  Fortify
 *  ALM
 *  Various build system test results
 *
 */
@Document(collection="code_quality")
public class CodeQuality extends BaseModel {
    private ObjectId collectorItemId;
    private long timestamp;

    private String name;
    private String url;
    private CodeQualityType type;
    private String version;
    private  ObjectId buildId;
    private String scanType;
    private String reportUrl;
    private long highSeverityCount;
    private long mediumSeverityCount;
    private long lowSeverityCount;


    private Set<CodeQualityMetric> metrics = new HashSet<>();

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CodeQualityType getType() {
        return type;
    }

    public void setType(CodeQualityType type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ObjectId getBuildId() {
        return buildId;
    }

    public void setBuildId(ObjectId buildId) {
        this.buildId = buildId;
    }

    public Set<CodeQualityMetric> getMetrics() {
        return metrics;
    }

    public void addMetric(CodeQualityMetric metric) {
        this.metrics.add(metric);
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }


    public long getHighSeverityCount() {
        return highSeverityCount;
    }

    public void setHighSeverityCount(long highSeverityCount) {
        this.highSeverityCount = highSeverityCount;
    }

    public long getMediumSeverityCount() {
        return mediumSeverityCount;
    }

    public void setMediumSeverityCount(long mediumSeverityCount) {
        this.mediumSeverityCount = mediumSeverityCount;
    }

    public long getLowSeverityCount() {
        return lowSeverityCount;
    }

    public void setLowSeverityCount(long lowSeverityCount) {
        this.lowSeverityCount = lowSeverityCount;
    }
}
