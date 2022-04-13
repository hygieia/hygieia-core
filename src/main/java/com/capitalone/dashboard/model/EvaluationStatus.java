package com.capitalone.dashboard.model;

public enum EvaluationStatus {

    PENDING,
    COMPLETED;


    public static EvaluationStatus fromString(String value) {
        for (EvaluationStatus evaluationStatus : values()) {
            if (evaluationStatus.toString().equalsIgnoreCase(value)) {
                return evaluationStatus;
            }
        }
        throw new IllegalArgumentException(value + " is not a Evaluation Type");
    }
}
