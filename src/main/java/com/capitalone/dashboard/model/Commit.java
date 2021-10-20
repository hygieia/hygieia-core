package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * A specific commit in a version control repository.
 *
 * Possible collectors:
 *  Subversion (in scope)
 *  Git (in scope)
 *  GitHub
 *  TFS
 *  BitBucket
 *  Unfuddle
 *
 */
@Document(collection="commits")
@CompoundIndexes({
        @CompoundIndex(name = "unique_scm_key", def = "{'collectorItemId' : 1, 'scmRevisionNumber': 1}")
})
public class Commit extends SCM {
    @Id
    private ObjectId id;
    @Indexed
    private ObjectId collectorItemId;
    private long timestamp;
    private boolean firstEverCommit;

    public Commit() {
        super();
        upsertTime = new Date(System.currentTimeMillis());
    }

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private Date upsertTime;

    private String clientReference;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFirstEverCommit() {
        return firstEverCommit;
    }

    public void setFirstEverCommit(boolean firstEverCommit) {
        this.firstEverCommit = firstEverCommit;
    }

    public Date getUpsertTime() {
        return upsertTime;
    }

    public void setUpsertTime(Date upsertTime) {
        this.upsertTime = upsertTime;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }
}
