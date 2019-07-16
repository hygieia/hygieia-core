package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="feature_flags")
public class FeatureFlag extends BaseModel {

    private boolean scm;
    private boolean codeQuality;
    private boolean libraryPolicy;
    private boolean staticSecurity;
    private boolean build;
    private boolean test;
    private boolean agileTool;
    private boolean artifact;
    private boolean deployment;

    public boolean isScm() {
        return scm;
    }

    public void setScm(boolean scm) {
        this.scm = scm;
    }

    public boolean isCodeQuality() {
        return codeQuality;
    }

    public void setCodeQuality(boolean codeQuality) {
        this.codeQuality = codeQuality;
    }

    public boolean isLibraryPolicy() {
        return libraryPolicy;
    }

    public void setLibraryPolicy(boolean libraryPolicy) {
        this.libraryPolicy = libraryPolicy;
    }

    public boolean isStaticSecurity() {
        return staticSecurity;
    }

    public void setStaticSecurity(boolean staticSecurity) {
        this.staticSecurity = staticSecurity;
    }

    public boolean isBuild() {
        return build;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public boolean isAgileTool() {
        return agileTool;
    }

    public void setAgileTool(boolean agileTool) {
        this.agileTool = agileTool;
    }

    public boolean isArtifact() {
        return artifact;
    }

    public void setArtifact(boolean artifact) {
        this.artifact = artifact;
    }

    public boolean isDeployment() {
        return deployment;
    }

    public void setDeployment(boolean deployment) {
        this.deployment = deployment;
    }



}
