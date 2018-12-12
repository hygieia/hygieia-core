package com.capitalone.dashboard.model;

import java.util.Arrays;

public enum LibraryPolicyThreatDisposition {
    Open,
    Closed,
    FalsePositive,
    WillNotFix,
    NotUsed,
    Challenged,
    ReviewRequested,
    Unknown;

    public static LibraryPolicyThreatDisposition fromString(String value) {
        return Arrays.stream(values())
                .filter(disposition -> disposition.toString()
                        .equalsIgnoreCase(value))
                .findFirst().orElse(Unknown);
    }
}