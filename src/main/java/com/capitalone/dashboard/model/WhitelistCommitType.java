package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

 @Document(collection="whitelist_commit_types")
public class WhitelistCommitType extends BaseModel{

    private String commitLogRegex;
    private boolean doContentCheck;
    private Map<String, String> contentPatterns;

    public WhitelistCommitType(String commitLogRegex, boolean doContentCheck, Map<String, String> contentPatterns) {
        this.commitLogRegex = commitLogRegex;
        this.doContentCheck = doContentCheck;
        this.contentPatterns = contentPatterns;
    }

    public String getCommitLogRegex() {
        return commitLogRegex;
    }

    public void setCommitLogRegex(String commitLogRegex) {
        this.commitLogRegex = commitLogRegex;
    }

    public boolean doContentCheck() { return doContentCheck; }

    public void setDoContentCheck(boolean doContentCheck) { this.doContentCheck = doContentCheck; }

     public Map<String, String> getContentPatterns() { return contentPatterns; }

     public String getContentPattern(String patternType) { return contentPatterns.get(patternType); }

    public void setContentPatterns(Map<String, String> contentPatterns) { this.contentPatterns = contentPatterns; }

}
