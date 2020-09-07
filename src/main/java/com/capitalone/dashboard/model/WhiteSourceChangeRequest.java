package com.capitalone.dashboard.model;


import java.util.List;
import java.util.Objects;

public class WhiteSourceChangeRequest extends BaseModel {

    private Long startDateTime;
    private String changeCategory;
    private String changeClass;
    private String changeAspect;
    private String scope;
    private String orgName;
    private String productName;
    private String projectName;
    private long lastUpdated;
    private String scopeName;
    private long changeScopeId;
    private List<String> beforeChange;
    private List<String> afterChange;
    private String operator;
    private String userEmail;
    private String orgId;

    public Long getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getChangeCategory() {
        return changeCategory;
    }

    public void setChangeCategory(String changeCategory) {
        this.changeCategory = changeCategory;
    }

    public String getChangeClass() {
        return changeClass;
    }

    public void setChangeClass(String changeClass) {
        this.changeClass = changeClass;
    }

    public String getChangeAspect() {
        return changeAspect;
    }

    public void setChangeAspect(String changeAspect) {
        this.changeAspect = changeAspect;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public long getChangeScopeId() {
        return changeScopeId;
    }

    public void setChangeScopeId(long changeScopeId) {
        this.changeScopeId = changeScopeId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getBeforeChange() {
        return beforeChange;
    }

    public void setBeforeChange(List<String> beforeChange) {
        this.beforeChange = beforeChange;
    }

    public List<String> getAfterChange() {
        return afterChange;
    }

    public void setAfterChange(List<String> afterChange) {
        this.afterChange = afterChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhiteSourceChangeRequest that = (WhiteSourceChangeRequest) o;
        return Objects.equals(getScopeName(), that.getScopeName());
    }

    @Override
    public int hashCode() {
        int result = 31 * getScopeName().hashCode();
        return result;
    }
}
