package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="service_accounts")
public class ServiceAccount extends BaseModel {

    private String serviceAccountName;
    private String fileNames;

    public ServiceAccount(String serviceAccountName, String fileNames) {
       this.serviceAccountName = serviceAccountName;
       this.fileNames = fileNames;
    }

    public String getServiceAccountName() {
        return serviceAccountName;
    }

    public void setServiceAccountName(String serviceAccountName) {
        this.serviceAccountName = serviceAccountName;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public String toString() {
        return "ServiceAccount [serviceAccount=" + serviceAccountName + ", fileNames=" + fileNames + "]";
    }
}
