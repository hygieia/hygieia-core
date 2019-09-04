package com.capitalone.dashboard.model;

public class AutoDiscoveryRemoteRequest extends AutoDiscovery {

    long createdTimeStamp;
    long modifiedTimeStamp;
    String autoDiscoveryId;

    public String getAutoDiscoveryId() { return autoDiscoveryId; }

    public void setAutoDiscoveryId(String autoDiscoveryId) { this.autoDiscoveryId = autoDiscoveryId; }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public long getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }

    public void setModifiedTimeStamp(long modifiedTimeStamp) {
        this.modifiedTimeStamp = modifiedTimeStamp;
    }
}
