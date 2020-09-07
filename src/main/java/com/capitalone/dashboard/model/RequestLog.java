package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "requests")
public class RequestLog extends BaseModel {

    @Indexed
    private String apiUser;
    private String client;
    @Indexed
    private String endpoint;
    private String method;
    private String parameter;
    private long requestSize;
    private String requestContentType;
    private Object requestBody;
    private long responseSize;
    private String responseContentType;
    private Object responseBody;
    private int responseCode;
    private long timestamp;
    private long responseTime;

    public String getApiUser() { return apiUser; }

    public void setApiUser(String apiUser) { this.apiUser = apiUser; }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public long getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(long requestSize) {
        this.requestSize = requestSize;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public String getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }

    public long getResponseTime() { return responseTime; }

    public void setResponseTime(long responseTime) { this.responseTime = responseTime; }

    public String toString() {
        return "REST Request - " + "[" + this.method + "] [PARAMETERS:" + parameter + "] + [APIUSER:" + apiUser + "] [BODY:" + requestBody + "] [REMOTE:" + client + "] [STATUS:" + responseCode + "] [RESPONSE TIME:" + responseTime + "]";
    }
}
