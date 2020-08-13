package com.capitalone.dashboard.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


@Document(collection = "library_policy")
public class LibraryPolicyResult extends BaseModel {
    private ObjectId collectorItemId;
    private long timestamp;
    private long evaluationTimestamp;
    private Map<LibraryPolicyType, Set<Threat>> threats = new HashMap<>();
    private String reportUrl;
    private Integer totalComponentCount;
    private Integer knownComponentCount;
    private List<PolicyScanMetric> policyAlert = new ArrayList<>();
    private ObjectId buildId;
    private String scanState;
    private String projectName;
    private String productName;
    private String buildUrl;

    public static class Threat {
        LibraryPolicyThreatLevel level;
        List<String> components = new ArrayList<>();
        int count;
        private Map<LibraryPolicyThreatDisposition, Integer> dispositionCounts = new HashMap<>();
        int maxAge;
        double maxScore;

        public Threat(LibraryPolicyThreatLevel level, int count) {
            this.level = level;
            this.count = count;
        }





        public LibraryPolicyThreatLevel getLevel() {
            return level;
        }

        public void setLevel(LibraryPolicyThreatLevel level) {
            this.level = level;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<String> getComponents() {
            return components;
        }

        public void setComponents(List<String> components) {
            this.components = components;
        }

        public void addDispositionCount(LibraryPolicyThreatDisposition disposition) {
            dispositionCounts.merge(disposition, 1, (a, b) -> a + b);
        }

        public int getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(int maxAge) {
            this.maxAge = maxAge;
        }

        public double getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(double maxScore) {
            this.maxScore = maxScore;
        }

        public Map<LibraryPolicyThreatDisposition, Integer> getDispositionCounts() {
            return dispositionCounts;
        }
    }

    public static class ThreatComponent {
        String name;

    }

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<LibraryPolicyType, Set<Threat>> getThreats() {
        return threats;
    }


    @Deprecated
    public void addThreat(LibraryPolicyType type, LibraryPolicyThreatLevel level, String component) {
        Set<Threat> threatSet = threats.get(type);

        if (CollectionUtils.isEmpty(threatSet)) {
            Threat threat = new Threat(level, 1);
            threat.getComponents().add(component);
            Set<Threat> set = new HashSet<>();
            set.add(threat);
            threats.put(type, set);
        } else {
            boolean found = false;
            for (Threat t : threatSet) {
                if (t.getLevel().equals(level)) {
                    t.setCount(t.getCount() + 1);
                    if (!t.getComponents().contains(component)) {
                        t.getComponents().add(component);
                    }
                    threatSet.add(t);
                    threats.put(type, threatSet);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Threat t = new Threat(level, 1);
                if (!t.getComponents().contains(component)) {
                    t.getComponents().add(component);
                }
                threatSet.add(t);
                threats.put(type, threatSet);
            }
        }
    }

    public void addThreat(LibraryPolicyType type, LibraryPolicyThreatLevel level, LibraryPolicyThreatDisposition disposition, String dispositionStatus, String component,String age,String score) {
        Set<Threat> threatSet = threats.get(type);

        if (CollectionUtils.isEmpty(threatSet)) {
            Threat threat = new Threat(level, 1);
            threat.getComponents().add(getComponentPlusDispositionPlusAge(component,disposition,dispositionStatus,age,score));
            threat.addDispositionCount(disposition);
            setCriticalAndHighVulAge(age, threat,disposition);
            setCriticalAndHighVulScore(score,threat,disposition);
            Set<Threat> set = new HashSet<>();
            set.add(threat);
            threats.put(type, set);
        } else {
            boolean found = false;
            for (Threat t : threatSet) {
                if (Objects.equals(t.getLevel(), level)) {
                    t.setCount(t.getCount() + 1);
                    t.addDispositionCount(disposition);
                    setCriticalAndHighVulAge(age, t,disposition);
                    setCriticalAndHighVulScore(score,t,disposition);
                    if (!t.getComponents().contains(getComponentPlusDispositionPlusAge(component,disposition,dispositionStatus,age,score))) {
                        t.getComponents().add(getComponentPlusDispositionPlusAge(component,disposition,dispositionStatus,age,score));
                    }
                    threatSet.add(t);
                    threats.put(type, threatSet);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Threat t = new Threat(level, 1);
                if (!t.getComponents().contains(getComponentPlusDispositionPlusAge(component,disposition,dispositionStatus,age,score))) {
                    t.getComponents().add(getComponentPlusDispositionPlusAge(component,disposition,dispositionStatus,age,score));
                }
                t.addDispositionCount(disposition);
                setCriticalAndHighVulAge(age, t,disposition);
                setCriticalAndHighVulScore(score,t,disposition);
                threatSet.add(t);
                threats.put(type, threatSet);
            }
        }

    }

    private void setCriticalAndHighVulAge(String age, Threat threat, LibraryPolicyThreatDisposition disposition) {
        if (disposition.equals(LibraryPolicyThreatDisposition.Open)) {
            int currentValue = NumberUtils.toInt(age);
            threat.setMaxAge(threat.getMaxAge() <= currentValue ? currentValue : threat.getMaxAge());
        }
    }

    private void setCriticalAndHighVulScore(String score, Threat threat, LibraryPolicyThreatDisposition disposition) {
        if (disposition.equals(LibraryPolicyThreatDisposition.Open)) {
            double currentScore = Double.parseDouble(score);
            threat.setMaxScore(threat.getMaxScore() <= currentScore ? currentScore : threat.getMaxScore());
        }
    }

    public void setThreats(Map<LibraryPolicyType, Set<Threat>> threats) {
        this.threats = threats;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public long getEvaluationTimestamp() {
        return evaluationTimestamp;
    }

    public void setEvaluationTimestamp(long evaluationTimestamp) {
        this.evaluationTimestamp = evaluationTimestamp;
    }

    public Integer getTotalComponentCount() {
        return totalComponentCount;
    }

    public void setTotalComponentCount(Integer totalComponentCount) {
        this.totalComponentCount = totalComponentCount;
    }

    public Integer getKnownComponentCount() {
        return knownComponentCount;
    }

    public void setKnownComponentCount(Integer knownComponentCount) {
        this.knownComponentCount = knownComponentCount;
    }

    public List<PolicyScanMetric> getPolicyAlert() {
        return policyAlert;
    }

    public void setPolicyAlert(List<PolicyScanMetric> policyAlert) {
        this.policyAlert = policyAlert;
    }

    private String getComponentPlusDisposition (String component, LibraryPolicyThreatDisposition disposition) {
        return String.format("%s##%s", component, disposition.toString());
    }

    private String getComponentPlusDispositionPlusAge (String component, LibraryPolicyThreatDisposition disposition, String dispositionStatus,String age, String score) {
        return String.format("%s##%s##%s##%s##%s", component, disposition.toString(), age, dispositionStatus,score);
    }


    public ObjectId getBuildId() { return buildId; }

    public void setBuildId(ObjectId buildId) { this.buildId = buildId; }

    public void setScanState(String scanState) { this.scanState = scanState; }

    public String getScanState() { return this.scanState; }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }
}
