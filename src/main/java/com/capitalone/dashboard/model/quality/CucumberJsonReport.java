package com.capitalone.dashboard.model.quality;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * Created by stevegal on 2019-03-24.
 */
public class CucumberJsonReport implements QualityVisitee {

    @Override
    public void accept(QualityVisitor visitor) {
        visitor.visit(this);
    }

    public static class Result {
        private long duration;
        private String status;

        public long getDuration() {
            return duration;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class Step {
        private String keyword;
        private String name;
        private String line;
        private Result result;

        public String getKeyword() {
            return keyword;
        }

        public Result getResult() {
            return result;
        }

        public String getLine() {
            return line;
        }

        public String getName() {
            return name;
        }
    }

    public static class Tag {
        String name;

        public String getName() {
            return name;
        }
    }

    public static class Match {
        private JsonNode location;


        public JsonNode getLocation() {
            return location;
        }

    }

    public static class Condition {
        private Match match;
        private Result result;

        public Match getMatch() {
            return match;
        }

        public Result getResult() {
            return result;
        }

    }

    public static class Element {
        private String id;
        private String keyword;
        private String name;
        private List<Step> steps;
        private List<Tag> tags;
        private List<Condition> before;
        private List<Condition> after;

        public String getKeyword() {
            return keyword;
        }

        public String getName() {
            return name;
        }

        public List<Step> getSteps() {
            return steps;
        }

        public String getId() {
            return id;
        }

        public List<Condition> getAfter() {
            return after;
        }

        public List<Condition> getBefore() {
            return before;
        }

        public List<Tag> getTags() {
            return tags;
        }
    }

    public static class Feature {
        private String id;
        private String keyword;
        private String name;

        private List<Element> elements;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getKeyword() {
            return keyword;
        }

        public List<Element> getElements() {
            return elements;
        }
    }

    @JsonCreator
    public CucumberJsonReport(List<Feature> features) {
        this.features = features;
    }

    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }
}
