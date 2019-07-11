package com.capitalone.dashboard.request;

import javax.validation.constraints.NotNull;

public class MetadataCreateRequest {

    @NotNull
    private String key;

    @NotNull
    private String type;

    @NotNull
    private Object rawData;

    private String source;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
