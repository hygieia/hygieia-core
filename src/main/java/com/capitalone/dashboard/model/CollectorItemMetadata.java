package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

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
@Document(collection="collector_item_metadata")
public class CollectorItemMetadata extends BaseModel {

    private ObjectId collectorId;
    private ObjectId collectorItemId;
    private long lastUpdated;
    private long createdTimestamp;

    private Map<String,Object> metadata = new HashMap<>();

    @Transient
    private  Collector collector;

    @Transient
    private CollectorItem collectorItem;

    public ObjectId getCollectorId() { return collectorId; }

    public void setCollectorId(ObjectId collectorId) { this.collectorId = collectorId; }

    public ObjectId getCollectorItemId() { return collectorItemId; }

    public void setCollectorItemId(ObjectId collectorItemId) { this.collectorItemId = collectorItemId; }

    public long getLastUpdated() { return lastUpdated; }

    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }

    public long getCreatedTimestamp() { return createdTimestamp; }

    public void setCreatedTimestamp(long createdTimestamp) { this.createdTimestamp = createdTimestamp; }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    public Collector getCollector() { return collector; }

    public void setCollector(Collector collector) { this.collector = collector; }

    public CollectorItem getCollectorItem() { return collectorItem; }

    public void setCollectorItem(CollectorItem collectorItem) { this.collectorItem = collectorItem; }
}
