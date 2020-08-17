package com.capitalone.dashboard.client;

public interface RestClientSettings {

    default int getRequestConnectTimeout() {
        return 5000;
    }

    default int getRequestReadTimeout() {
        return 60000;
    }
}
