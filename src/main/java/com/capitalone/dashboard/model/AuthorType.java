package com.capitalone.dashboard.model;

public enum AuthorType {
    User,
    Bot;

    public static AuthorType fromString(String value) {
        for (AuthorType authorType : values()) {
            if (authorType.toString().equalsIgnoreCase(value)) {
                return authorType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Author Type");
    }
}
