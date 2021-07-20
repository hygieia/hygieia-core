package com.capitalone.dashboard.model;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *      Represents a unique collection in an external tool. For example, for a CI tool
 *      the collector item would be a Job. For a project management tool, the collector item
 *      might be a Scope.
 * </p>
 * <p>
 *      Each {@link Collector} is responsible for specifying how it's {@link CollectorItem}s are
 *      uniquely identified by storing key/value pairs in the options Map. The description field will
 *      be visible to users in the UI to aid in selecting the correct {@link CollectorItem} for their dashboard.
 *      Ideally, the description will be unique for a given {@link Collector}.
 * </p>
 */
@Document(collection="collector_items")
public class CollectorItem extends BaseModel {

    private String description;
    private String niceName;
    private String environment;
    private boolean enabled;
    private List<CollectionError> errors = new ArrayList<>();
    private boolean pushed;
    private ObjectId collectorId;
    private long lastUpdated;
    private Map<String,Object> options = new HashMap<>();
    private String refreshLink;

    @Transient
    private Collector collector;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ObjectId getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(ObjectId collectorId) {
        this.collectorId = collectorId;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public Map<String, Object> getOptions() {
        return options;
    }
    public void setOptions(Map<String,Object> options) {
        this.options = options;
    }
    public boolean isPushed() {
        return pushed;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public List<CollectionError> getErrors() {
        return errors;
    }
    public int getErrorCount() {
        return errors.size();
    }

    public String getRefreshLink() { return refreshLink; }

    public void setRefreshLink(String refreshLink) { this.refreshLink = refreshLink; }


    /**
     * Checks for collector items error threshold and resets it if window is met
     * @param resetWindow - long time in milliseconds after which errors can be reset
     * @param errorThreshold - number of errors to cross threshold
     * @return true if (a) errors are within threshold and (b) reset if window is passed.
     */
    public boolean checkErrorOrReset (long resetWindow, int errorThreshold) {
        Optional<CollectionError> lastErrorOptional = errors.stream().max(Comparator.comparingLong(CollectionError::getTimestamp));
        long lastErrorTimestamp = lastErrorOptional.isPresent() ? lastErrorOptional.get().getTimestamp() : System.currentTimeMillis();
        if ((System.currentTimeMillis() - lastErrorTimestamp) >= resetWindow) {
            //clear the oldest error so errors count drops below threshold
            if (!CollectionUtils.isEmpty(errors)) {
                errors.sort(Comparator.comparing(CollectionError::getTimestamp));
                errors.remove(0);
            }
            return true;
        } else {
            return (errors.size() <= errorThreshold);
        }
    }
}
