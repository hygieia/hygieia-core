package com.capitalone.dashboard.event.constants.sync;

public enum Reason {
    BUILD_REPO_REASON("Code Repo build"),
    CODEQUALITY_TRIGGERED_REASON("Code scan triggered by build"),
    ARTIFACT_REASON ("Artifact pushed by build");

    private String action;

    public String getAction() {
        return action;
    }

    Reason(String action) {
        this.action = action;
    }
}
