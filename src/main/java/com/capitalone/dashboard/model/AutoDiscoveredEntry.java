package com.capitalone.dashboard.model;

import com.capitalone.dashboard.misc.HygieiaException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
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

    boolean pushed = false;

    boolean enabled = false;

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


    public boolean isPushed() {
        return pushed;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public void setStatus(AutoDiscoveryStatusType status) {
        this.status = status;
    }

    public AutoDiscoverCollectorItem toAutoDiscoverCollectorItem(Collector collector) throws HygieiaException {
        if (options.keySet().containsAll(collector.getUniqueFields().keySet())) {
            AutoDiscoverCollectorItem collectorItem = new AutoDiscoverCollectorItem();
            collectorItem.setEnabled(true);
            collectorItem.setPushed(isPushed());
            collectorItem.setDescription(description);
            collectorItem.setNiceName(niceName);
            collectorItem.setCollectorId(collector.getId());
            for (String key : options.keySet()) {
                if (collector.getAllFields().keySet().contains(key)) {
                    collectorItem.getOptions().put(key, options.get(key));
                }
            }
            collectorItem.setAutoDiscoverStatus(status);
            return collectorItem;
        } else {
            throw new HygieiaException("Missing required fields. " + toolName + " collector required fields are: " + String.join(", ", collector.getUniqueFields().keySet()), HygieiaException.COLLECTOR_ITEM_CREATE_ERROR);
        }
    }
}
