package com.capitalone.dashboard.model;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * AutoDiscovery Metadata
 */
public class AutoDiscoveryMetaData {
    @NotNull
    private String template;

    @NotNull
    private String type;

    @NotNull
    @Size(min = 6, max = 50)
    @Pattern(message = "Special character(s) found", regexp = "^[a-zA-Z0-9 ]*$")
    private String title;

    @NotNull
    private String applicationName;

    @NotNull
    private String componentName;

    private String businessService;

    private String businessApplication;

    @Ignore
    private Owner owner;

    @Ignore
    private List<Owner> owners;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Owner getOwner() { return owner; }

    public void setOwner(Owner owner) { this.owner = owner; }

    public List<Owner> getOwners() { return owners; }

    public void setOwners(List<Owner> owners) { this.owners = owners; }

    public AutoDiscoveryMetaData(){ }
}
