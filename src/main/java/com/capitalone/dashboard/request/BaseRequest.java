package com.capitalone.dashboard.request;

public class BaseRequest {

    private String clientReference;

    public String getClientReference() { return clientReference; }

    public void setClientReference(String clientReference) { this.clientReference = clientReference; }

    /*
     * Avoid implementing equals and hashcode for BaseClass as it will create more issues.
     */
}
