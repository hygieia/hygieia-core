package com.capitalone.dashboard.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entry class to hold the Auto Discovered entries: Jira project, github project, build job etc.
 */
public class AutoDiscoveredEntry {
    @NotNull
    AutoDiscoveryStatusType status;

    @NotNull
    String toolName;

    @NotNull
    String description;

    String niceName;

    @NotEmpty
    Map<String, Object> options = new HashMap<>();

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public AutoDiscoveryStatusType getStatus() {
        return status;
    }

    public void setStatus(AutoDiscoveryStatusType status) {
        this.status = status;
    }

}
