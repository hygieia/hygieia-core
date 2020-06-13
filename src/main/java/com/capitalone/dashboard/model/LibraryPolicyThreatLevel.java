package com.capitalone.dashboard.model;

/**
 * Enumerates the possible statuses.
 */
public enum LibraryPolicyThreatLevel {
    Critical, High, Medium, Low, None, Informational;

    public static LibraryPolicyThreatLevel fromString(String value){
        for(LibraryPolicyThreatLevel threatLevel : values()){
            if(threatLevel.toString().equalsIgnoreCase(value)){
                return threatLevel;
            }
        }
        throw new IllegalArgumentException(value+" is not a valid LibraryPolicyThreatLevel");
    }
}
