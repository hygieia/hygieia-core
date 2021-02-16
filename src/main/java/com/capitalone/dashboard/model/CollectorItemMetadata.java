package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Document(collection="collector_item_metadata")
public class CollectorItemMetadata extends BaseModel {

    private ObjectId collectorId;
    private ObjectId collectorItemId;
    private CollectorType collectorType;
    private long lastUpdated;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private java.util.Date lastUpdatedTime;

    private Map<String,Object> metadata = new HashMap<>();

    public ObjectId getCollectorId() { return collectorId; }

    public void setCollectorId(ObjectId collectorId) { this.collectorId = collectorId; }

    public ObjectId getCollectorItemId() { return collectorItemId; }

    public void setCollectorItemId(ObjectId collectorItemId) { this.collectorItemId = collectorItemId; }

    public long getLastUpdated() { return lastUpdated; }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
        this.setLastUpdatedTime(new Date(lastUpdated));
    }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    public CollectorType getCollectorType() { return collectorType; }

    public void setCollectorType(CollectorType collectorType) { this.collectorType = collectorType; }

    public Date getLastUpdatedTime() {

        return this.lastUpdated == 0 ? new Date(System.currentTimeMillis()) : new Date(this.lastUpdated);
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) { this.lastUpdatedTime = lastUpdatedTime; }

}
