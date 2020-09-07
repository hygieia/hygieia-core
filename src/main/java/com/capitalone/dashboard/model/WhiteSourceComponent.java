package com.capitalone.dashboard.model;


import java.util.Objects;

public class WhiteSourceComponent extends CollectorItem {
    public static final String PRODUCT_NAME = "productName";
    public static final String PROJECT_NAME = "projectName";
    public static final String PRODUCT_TOKEN = "productToken";
    public static final String PROJECT_TOKEN = "projectToken";
    public static final String ORG_NAME = "orgName";


    public String getOrgName() {
       return  (String) getOptions().get(ORG_NAME);
    }
    public void setOrgName(String orgName){
        getOptions().put(ORG_NAME,orgName);
    }

    public  String getProductName() {
        return (String) getOptions().get(PRODUCT_NAME);
    }
    public void setProductName(String productName) {
        getOptions().put(PRODUCT_NAME, productName);
    }


    public  String getProjectName() {
        return (String) getOptions().get(PROJECT_NAME);
    }

    public void setProjectName(String projectName) {
        getOptions().put(PROJECT_NAME, projectName);
    }


    public  String getProductToken() {
        return (String) getOptions().get(PRODUCT_TOKEN);
    }

    public void setProductToken(String productToken) {
        getOptions().put(PRODUCT_TOKEN, productToken);
    }


    public  String getProjectToken() {
        return (String) getOptions().get(PROJECT_TOKEN);
    }

    public void setProjectToken(String projectToken) {
        getOptions().put(PROJECT_TOKEN, projectToken);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhiteSourceComponent that = (WhiteSourceComponent) o;
        return Objects.equals(getProductName(), that.getProductName()) && Objects.equals(getProjectName(), that.getProjectName()) && Objects.equals(getOrgName(), that.getOrgName());
    }

    @Override
    public int hashCode() {
        int result = 31 * getProductName().hashCode()+ getProjectName().hashCode()+ getOrgName().hashCode();
        return result;
    }
}
