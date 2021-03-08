package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Base class for all Mongo model classes that has an id property.
 */
public class BaseModel {

    public BaseModel() {
        upsertTime = new Date(System.currentTimeMillis());
    }

    @Id
    private ObjectId id;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private Date upsertTime;

    private String clientReference;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getUpsertTime() { return upsertTime; }

    public void setUpsertTime(Date upsertTime) { this.upsertTime = upsertTime; }

    public String getClientReference() { return clientReference; }

    public void setClientReference(String clientReference) { this.clientReference = clientReference; }

    /*
     * Note:
     * 
     * Having hashcode + equals is more complicated than simply comparing ObjectIds since
     * it does not provide a way to properly compare models that have not been saved yet.
     */
}
