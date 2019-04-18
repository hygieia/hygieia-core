package com.capitalone.dashboard.model.quality;

import java.util.List;

/**
 * Resutls from MochaJSSpec Format
 */
public class MochaJsSpecReport implements QualityVisitee {

    public static class Stats {
        private int suites;
        private int tests;
        private int passes;
        private int failures;
        private int pending;
        private long duration;

        public int getFailures() {
            return failures;
        }

        public int getPending() {
            return pending;
        }

        public int getSuites() {
            return suites;
        }

        public int getPasses() {
            return passes;
        }

        public int getTests() {
            return tests;
        }

        public long getDuration() {
            return duration;
        }
    }

    public static class Suite {
        private String title;
        private List<Test> tests;

        public String getTitle() {
            return title;
        }

        public List<Test> getTests() {
            return tests;
        }
    }

    public static class Test {
        private String title;
        private long duration;
        private String result;

        public String getTitle() {
            return title;
        }

        public long getDuration() {
            return duration;
        }

        public String getResult() {
            return result;
        }
    }

    private Stats stats;

    private List<Suite> suites;

    public Stats getStats() {
        return stats;
    }

    public List<Suite> getSuites() {
        return suites;
    }

    @Override
    public void accept(QualityVisitor visitor) {
        visitor.visit(this);
    }
}
