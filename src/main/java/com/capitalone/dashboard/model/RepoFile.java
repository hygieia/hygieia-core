package com.capitalone.dashboard.model;

public class RepoFile {
    private String filename;
    private String patch;
    private String sha;
    private String status;
    private int additions;
    private int deletions;
    private int changes;
    private String blob_url;
    private String raw_url;
    private String contents_url;

    public RepoFile () {

    }

    public RepoFile (String filename, String patch, String sha, String status, int additions, int deletions, int changes,
                    String blob_url, String raw_url, String contents_url) {
        this.filename = filename;
        this. patch = patch;
        this.sha = sha;
        this.status = status;
        this.additions = additions;
        this.deletions = deletions;
        this.changes = changes;
        this.blob_url = blob_url;
        this.raw_url = raw_url;
        this.contents_url = contents_url;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPatch() {
        return this.patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public String getSha() {
        return this.sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAdditions() {
        return this.additions;
    }

    public void setAdditions(int additions) {
        this.additions = additions;
    }

    public int getDeletions() {
        return this.deletions;
    }

    public void setDeletions(int deletions) {
        this.deletions = deletions;
    }

    public int getChanges() {
        return this.changes;
    }

    public void setChanges(int changes) {
        this.changes = changes;
    }

    public String getBlob_url() {
        return this.blob_url;
    }

    public void setBlob_url(String blob_url) {
        this.blob_url = blob_url;
    }

    public String getRaw_url() {
        return this.raw_url;
    }

    public void setRaw_url(String raw_url) {
        this.raw_url = raw_url;
    }

    public String getContents_url() {
        return this.contents_url;
    }

    public void setContents_url(String contents_url) {
        this.contents_url = contents_url;
    }
}

