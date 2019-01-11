package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Document(collection = "code_repos_builds")
public class CodeReposBuilds extends BaseModel {

    @Indexed(unique = true)
    private String codeRepo;

    private Set<ObjectId> buildCollectorItems = new HashSet<>();

    @Indexed
    private long timestamp;

    public String getCodeRepo() {
        return codeRepo.toLowerCase(Locale.US);
    }

    public void setCodeRepo(String codeRepo) {
        this.codeRepo = codeRepo.toLowerCase(Locale.US);
    }

    public Set<ObjectId> getBuildCollectorItems() {
        return buildCollectorItems;
    }

    public void setBuildCollectorItems(Set<ObjectId> buildCollectorItems) {
        this.buildCollectorItems = buildCollectorItems;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
