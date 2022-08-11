package com.capitalone.dashboard.model;


public enum LibraryPolicyType {
    Security,
    License,
    Security_cvss3,
    Other;

    public static LibraryPolicyType fromString(String value) {
        for (LibraryPolicyType policyType : values()) {
            if (policyType.toString().equalsIgnoreCase(value)) {
                return policyType;
            }
        }
        return Other;
    }
}
