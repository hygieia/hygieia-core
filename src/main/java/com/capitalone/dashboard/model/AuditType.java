package com.capitalone.dashboard.model;

public enum AuditType {

    ALL,
    CODE_REVIEW,
    BUILD_REVIEW,
    CODE_QUALITY,
    STATIC_SECURITY_ANALYSIS,
    LIBRARY_POLICY,
    TEST_RESULT,
    PERF_TEST,
    ARTIFACT,
    DEPLOY,
    AUTO_DISCOVER,
    INFRASTRUCTURE_SCAN;


    public static AuditType fromString(String value) {
        for (AuditType auditType : values()) {
            if (auditType.toString().equalsIgnoreCase(value)) {
                return auditType;
            }
        }
        throw new IllegalArgumentException(value + " is not a Audit Type");
    }
}
