package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "library_reference")
public class LibraryPolicyReference extends BaseModel {
    @Indexed
    @NotNull
    private String libraryName;
    private String changeClass;
    private String operator;
    private String userEmail;
    private List<WhiteSourceComponent> projectReferences = new ArrayList<>();
    private long lastUpdated;
    private String orgName;
    private String language;

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public List<WhiteSourceComponent> getProjectReferences() {
        return projectReferences;
    }

    public void setProjectReferences(List<WhiteSourceComponent> projectReferences) {
        this.projectReferences = projectReferences;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getChangeClass() {
        return changeClass;
    }

    public void setChangeClass(String changeClass) {
        this.changeClass = changeClass;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }
}
