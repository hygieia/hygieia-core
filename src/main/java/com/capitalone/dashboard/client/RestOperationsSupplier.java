package com.capitalone.dashboard.client;

import org.springframework.web.client.RestOperations;

/**
 * Supplier that returns an instance of RestOperations
 */
public interface RestOperationsSupplier {

    public RestOperations get(RestClientSettings settings);


}
