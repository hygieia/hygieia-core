package com.capitalone.dashboard.model;

public enum PullRequestEvent {
    Assigned,
    Unassigned,
    Review_requested,
    Review_request_removed,
    Labeled,
    Unlabeled,
    Opened,
    Edited,
    Closed,
    Ready_for_review,
    Locked,
    Unlocked,
    Reopened,
    Merged,
    Synchronize;


    public static PullRequestEvent fromString(String value) {
        for (PullRequestEvent event : values()) {
            if (event.toString().equalsIgnoreCase(value)) {
                return event;
            }
        }
        return null;
    }
}


