package com.capitalone.dashboard.model;

public class GitHubCollector extends Collector {
    private long latestProcessedEventTimestamp;
    private long latestProcessedEventId;

    private long lastCleanUpTimestamp;
    private long lastPrivateRepoCollectionTimestamp;


    public long getLatestProcessedEventTimestamp() {
        return latestProcessedEventTimestamp;
    }

    public void setLatestProcessedEventTimestamp(long latestProcessedEventTimestamp) {
        this.latestProcessedEventTimestamp = latestProcessedEventTimestamp;
    }

    public long getLatestProcessedEventId() {
        return latestProcessedEventId;
    }

    public void setLatestProcessedEventId(long latestProcessedEventId) {
        this.latestProcessedEventId = latestProcessedEventId;
    }

    public long getLastCleanUpTimestamp() {
        return lastCleanUpTimestamp;
    }

    public void setLastCleanUpTimestamp(long lastCleanUpTimestamp) {
        this.lastCleanUpTimestamp = lastCleanUpTimestamp;
    }

    public long getLastPrivateRepoCollectionTimestamp() {
        return lastPrivateRepoCollectionTimestamp;
    }

    public void setLastPrivateRepoCollectionTimestamp(long lastPrivateRepoCollectionTimestamp) {
        this.lastPrivateRepoCollectionTimestamp = lastPrivateRepoCollectionTimestamp;
    }
}
