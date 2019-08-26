package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * A collection of auto discovered end points that represent a software
 * project under development and/or in production use.
 *
 */
@Document(collection="auto_discovery_status")
public class AutoDiscovery extends BaseModel {
    @Valid
    private AutoDiscoveryMetaData metaData;

    @Valid
    private List<AutoDiscoveredEntry> codeRepoEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> buildEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> securityScanEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> deploymentEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> libraryScanEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> functionalTestEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> artifactEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> staticCodeEntries = new ArrayList<>();

    @Valid
    private List<AutoDiscoveredEntry> featureEntries = new ArrayList<>();

    public  AutoDiscovery() {
        metaData = null;
    }
    public AutoDiscovery(AutoDiscoveryMetaData metaData, List<AutoDiscoveredEntry> codeRepoEntries, List<AutoDiscoveredEntry> buildEntries, List<AutoDiscoveredEntry> securityScanEntries,
                         List<AutoDiscoveredEntry> deploymentEntries, List<AutoDiscoveredEntry> libraryScanEntries, List<AutoDiscoveredEntry> functionalTestEntries,
                         List<AutoDiscoveredEntry> artifactEntries, List<AutoDiscoveredEntry> staticCodeEntries, List<AutoDiscoveredEntry> featureEntries) {
        setMetaData(metaData);
        setCodeRepoEntries(codeRepoEntries);
        setBuildEntries(buildEntries);
        setSecurityScanEntries(securityScanEntries);
        setDeploymentEntries(deploymentEntries);
        setLibraryScanEntries(libraryScanEntries);
        setFunctionalTestEntries(functionalTestEntries);
        setArtifactEntries(artifactEntries);
        setStaticCodeEntries(staticCodeEntries);
        setFeatureEntries(featureEntries);
    }


    // Getters and setters

    public AutoDiscoveryMetaData getMetaData() { return metaData; }

    public void setMetaData(AutoDiscoveryMetaData metaData) { this.metaData = metaData; }

    public List<AutoDiscoveredEntry> getCodeRepoEntries() {
        return codeRepoEntries;
    }

    public void setCodeRepoEntries(List<AutoDiscoveredEntry> codeRepoEntries) {
        this.codeRepoEntries = codeRepoEntries;
    }

    public List<AutoDiscoveredEntry> getBuildEntries() {
        return buildEntries;
    }

    public void setBuildEntries(List<AutoDiscoveredEntry> buildEntries) {
        this.buildEntries = buildEntries;
    }

    public List<AutoDiscoveredEntry> getSecurityScanEntries() {
        return securityScanEntries;
    }

    public void setSecurityScanEntries(List<AutoDiscoveredEntry> securityScanEntries) {
        this.securityScanEntries = securityScanEntries;
    }

    public List<AutoDiscoveredEntry> getDeploymentEntries() {
        return deploymentEntries;
    }

    public void setDeploymentEntries(List<AutoDiscoveredEntry> deploymentEntries) {
        this.deploymentEntries = deploymentEntries;
    }

    public List<AutoDiscoveredEntry> getLibraryScanEntries() {
        return libraryScanEntries;
    }

    public void setLibraryScanEntries(List<AutoDiscoveredEntry> libraryScanEntries) {
        this.libraryScanEntries = libraryScanEntries;
    }

    public List<AutoDiscoveredEntry> getFunctionalTestEntries() {
        return functionalTestEntries;
    }

    public void setFunctionalTestEntries(List<AutoDiscoveredEntry> functionalTestEntries) {
        this.functionalTestEntries = functionalTestEntries;
    }

    public List<AutoDiscoveredEntry> getArtifactEntries() {
        return artifactEntries;
    }

    public void setArtifactEntries(List<AutoDiscoveredEntry> artifactEntries) {
        this.artifactEntries = artifactEntries;
    }

    public List<AutoDiscoveredEntry> getStaticCodeEntries() { return staticCodeEntries; }

    public void setStaticCodeEntries(List<AutoDiscoveredEntry> staticCodeEntries) {
        this.staticCodeEntries = staticCodeEntries;
    }

    public List<AutoDiscoveredEntry> getFeatureEntries() { return featureEntries; }

    public void setFeatureEntries(List<AutoDiscoveredEntry> featureEntries) {
        this.featureEntries = featureEntries;
    }

    public List<AutoDiscoveredEntry> getAllEntries() {
        List<AutoDiscoveredEntry> all = new ArrayList<>();
        all.addAll(buildEntries);
        all.addAll(codeRepoEntries);
        all.addAll(staticCodeEntries);
        all.addAll(libraryScanEntries);
        all.addAll(securityScanEntries);
        all.addAll(functionalTestEntries);
        all.addAll(deploymentEntries);
        all.addAll(featureEntries);
        all.addAll(artifactEntries);
        return all;
    }
}
