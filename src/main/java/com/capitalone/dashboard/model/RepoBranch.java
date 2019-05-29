package com.capitalone.dashboard.model;

import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Locale;

public class RepoBranch {
    private String url = "";
    private String branch = "";
    private RepoType type = RepoType.Unknown;
    private static final String GIT_EXTN = ".git";
    private static final String GIT_SCHEME = "git@";
    private static final String DEFAULT_SCHEME = "https://";
    private static final String GIT_SEPERATOR = ":";
    private static final String DEFAULT_SEPERATOR = "/";

    public enum RepoType {
        SVN,
        GIT,
        Unknown;

        public static com.capitalone.dashboard.model.RepoBranch.RepoType fromString(String value) {
            if (value ==  null) return RepoType.Unknown;
            for (com.capitalone.dashboard.model.RepoBranch.RepoType repoType : values()) {
                if (repoType.toString().equalsIgnoreCase(value)) {
                    return repoType;
                }
            }
            throw new IllegalArgumentException(value + " is not a valid RepoType.");
        }
    }

    public RepoBranch(String url, String branch, RepoType repoType) {
        this.url = url;
        this.branch = branch;
        this.type = repoType;
    }

    public RepoBranch() {
    }

    public String getUrl() {
        switch (this.type) {
            case GIT:
                return getGITNormalizedURL(this.url);
            case SVN:
                return this.url;
            default:
                return this.url;
        }
    }

    public void setUrl(String url) {
        this.url = url.toLowerCase(Locale.US);
    }

    public String getBranch() {
        switch (this.getType()) {
            case GIT: return getGITNormalizedBranch(branch);
            case SVN: return branch;
            default: return branch;
        }
    }

    public void setBranch(String branch) {
        switch (this.getType()) {
            case GIT: this.branch = getGITNormalizedBranch(branch); break;
            case SVN: this.branch = branch; break;
            default: this.branch = branch; break;
        }
    }

    public RepoType getType() {
        return type;
    }

    public void setType(RepoType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepoBranch that = (RepoBranch) o;

        return getRepoName().equals(that.getRepoName()) && getBranch().equals(that.getBranch());
    }

    @Override
    public int hashCode() {
        int result = this.getUrl().hashCode();
        result = 31 * result + getBranch().hashCode();
        return result;
    }

    private String getRepoName() {
        try {
            URL temp = new URL(getUrl());
            return temp.getHost() + temp.getPath();
        } catch (MalformedURLException e) {
            return getUrl();
        }

    }

    private String getGITNormalizedBranch (@NotNull String branch) {
        String[] tokens = branch.split("/");
        return tokens[tokens.length-1];
    }

    private String getGITNormalizedURL(@NotNull String url) {
        // check for http or https or ssh or git
        if (url.endsWith(GIT_EXTN)) {
            url = url.substring(0, url.lastIndexOf(GIT_EXTN));
        }

        if (url.contains(GIT_SCHEME)) {
            url = url.replace(GIT_SEPERATOR, DEFAULT_SEPERATOR);
            url = url.replace(GIT_SCHEME, DEFAULT_SCHEME);
        }

        String host;
        String path;
        try {
            URI processedURI = URI.create(url.replaceAll(" ", "%20"));
            host = processedURI.getHost();
            path = processedURI.getPath();
        } catch (IllegalArgumentException e) {
            return url;
        }

        /*
         * Force the urls to use https just as a means of Normalization.
         * */
        return DEFAULT_SCHEME + host + path;
    }

}
