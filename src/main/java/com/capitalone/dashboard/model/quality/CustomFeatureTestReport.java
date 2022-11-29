package com.capitalone.dashboard.model.quality;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public class CustomFeatureTestReport implements QualityVisitee {

    @Override
    public void accept(QualityVisitor visitor) {
        visitor.visit(this);
    }

    public static class CustomFeatureTestCase {
        private String testCaseId;
        private String status;

        public String getTestCaseId() {
            return testCaseId;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class Feature {
        private String testId;
        private String status;
        private String testSetName;
        private String testType;
        private String testAgentType;
        private String componentName;
        private String testRequestId;
        private List<String> tags;
        // Typo correctly matches report schema
        private String accessbilityTestResultStatus;
        private String testRunStatus;
        private Integer testCasesCount;
        private Integer testCasesPassedCount;
        private Integer testCasesFailedCount;
        private Integer testCasesSkippedCount;
        private List<CustomFeatureTestCase> testCases;

        public String getTestId(){ return testId; }
        public String getStatus(){ return status; }
        public String getTestSetName(){ return testSetName; }
        public String getTestType(){ return testType; }
        public String getTestAgentType(){ return testAgentType; }
        public String getComponentName(){ return componentName; }
        public String getTestRequestId(){ return testRequestId; }
        public List<String> getTags(){ return tags; }
        public String getAccessbilityTestResultStatus() { return accessbilityTestResultStatus; }
        public String getTestRunStatus() { return testRunStatus; }
        public Integer getTestCasesCount() { return testCasesCount; }
        public Integer getTestCasesFailedCount() { return testCasesFailedCount; }
        public Integer getTestCasesPassedCount() { return testCasesPassedCount; }
        public Integer getTestCasesSkippedCount() { return testCasesSkippedCount; }
        public List<CustomFeatureTestCase> getTestCases() { return testCases; }
    }

    @JsonCreator
    public CustomFeatureTestReport(List<Feature> features) {

        this.features = features;
    }

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

}
