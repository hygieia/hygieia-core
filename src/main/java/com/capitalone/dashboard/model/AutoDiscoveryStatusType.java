package com.capitalone.dashboard.model;

/**
 * Defines the type of AutoDiscoveredEntry.status.
 */
public enum AutoDiscoveryStatusType {
    AWAITING_USER_RESPONSE,
    USER_ACCEPTED,
    USER_REJECTED;

    public static AutoDiscoveryStatusType fromString(String value){
        for(AutoDiscoveryStatusType statusType : values()){
            if(statusType.toString().equalsIgnoreCase(value)){
                return statusType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid AutoDiscoveryStatusType");
    }
}
