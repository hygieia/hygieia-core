package com.capitalone.dashboard.model;

import java.util.List;

public class AutoDiscoverySubscriberRemoteRequest {

    List<String> systemsToRequest;
    AutoDiscoveryRemoteRequest autoDiscoveryRequest;

    public List<String> getSystemsToRequest() {
        return systemsToRequest;
    }

    public void setSystemsToRequest(List<String> systemsToRequest) {
        this.systemsToRequest = systemsToRequest;
    }

    public AutoDiscoveryRemoteRequest getAutoDiscoveryRequest() {
        return autoDiscoveryRequest;
    }

    public void setAutoDiscoveryRequest(AutoDiscoveryRemoteRequest autoDiscoveryRequest) {
        this.autoDiscoveryRequest = autoDiscoveryRequest;
    }
}
