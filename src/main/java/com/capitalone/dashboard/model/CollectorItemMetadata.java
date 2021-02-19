package com.capitalone.dashboard.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

        return lastUpdatedTime == null ? new Date(System.currentTimeMillis()) : lastUpdatedTime ;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) { this.lastUpdatedTime = lastUpdatedTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CollectorItemMetadata that = (CollectorItemMetadata) o;

        return new EqualsBuilder()
                .append(collectorId, that.collectorId)
                .append(collectorItemId, that.collectorItemId)
                .append(collectorType, that.collectorType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(collectorId)
                .append(collectorItemId)
                .append(collectorType)
                .toHashCode();
    }

}
