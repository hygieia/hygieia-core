package com.capitalone.dashboard.model;

import com.capitalone.dashboard.misc.HygieiaException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry class to hold the Auto Discovered entries: Jira project, github project, build job etc.
 */
public class AutoDiscoveredEntry {
    @NotNull
    private AutoDiscoveryStatusType status;

    @NotNull
    private String toolName;

    @NotNull
    private String description;

    private String niceName;

    private boolean pushed = false;

    private boolean enabled = false;

    @NotEmpty
    private Map<String, Object> options = new HashMap<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AutoDiscoveredEntry that = (AutoDiscoveredEntry) o;

        return new EqualsBuilder()
                .append(status, that.status)
                .append(description, that.description)
                .append(options, that.options)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(status)
                .append(description)
                .append(options)
                .toHashCode();
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
