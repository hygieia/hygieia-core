package com.capitalone.dashboard.response;

public class BaseResponse {

    private String clientReference;

    public String getClientReference() { return clientReference; }

    public void setClientReference(String clientReference) { this.clientReference = clientReference; }

    /*
     * Avoid implementing equals and hashcode for BaseClass as it will create more issues.
     */
}
