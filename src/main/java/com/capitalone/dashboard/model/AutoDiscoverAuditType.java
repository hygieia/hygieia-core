package com.capitalone.dashboard.model;

public enum AutoDiscoverAuditType {

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
    AUTO_DISCOVER;


    public static AutoDiscoverAuditType fromString(String value) {
        for (AutoDiscoverAuditType auditType : values()) {
            if (auditType.toString().equalsIgnoreCase(value)) {
                return auditType;
            }
        }
        throw new IllegalArgumentException(value + " is not a AutoDiscover Audit Type");
    }
}
