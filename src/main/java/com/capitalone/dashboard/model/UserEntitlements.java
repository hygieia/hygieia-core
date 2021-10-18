package com.capitalone.dashboard.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user_entitlements")
public class UserEntitlements extends BaseModel {

    private String username;
    private AuthType authType;
    private String entitlementType;
    private String entitlements;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getEntitlementType() {
        return entitlementType;
    }

    public void setEntitlementType(String entitlementType) {
        this.entitlementType = entitlementType;
    }

    public String getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(String entitlements) {
        this.entitlements = entitlements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserEntitlements that = (UserEntitlements) o;

        return new EqualsBuilder()
                .append(username, that.username)
                .append(authType, that.authType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(username)
                .append(authType)
                .toHashCode();
    }
}
