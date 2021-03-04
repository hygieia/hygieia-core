package com.capitalone.dashboard.request;

import com.capitalone.dashboard.model.SCM;

public class GitRequestCreateRequest extends SCM {
    private String hygieiaId;

    private long timestamp;

    private String clientReference;

    public String getClientReference() { return clientReference; }

    public void setClientReference(String clientReference) { this.clientReference = clientReference; }

    public String getHygieiaId() {
        return hygieiaId;
    }

    public void setHygieiaId(String hygieiaId) {
        this.hygieiaId = hygieiaId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
