package com.capitalone.dashboard.model.quality;

/**
 * Resutls from MochaJSSpec Format
 */
public class MochaJsSpecReport implements CodeQualityVisitee {

    public static class Stats {
        private int suites;
        private int tests;
        private int passes;
        private int failures;
        private int pending;

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
    }

    private Stats stats;

    public Stats getStats() {
        return stats;
    }

    @Override
    public void accept(CodeQualityVisitor visitor) {
        visitor.visit(this);
    }
}
