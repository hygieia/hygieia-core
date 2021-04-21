package com.capitalone.dashboard.model;


public class RepositoryDigest {

    private String digest;
    private String repository;
    private String registry;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }
}
