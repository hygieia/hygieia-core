package com.capitalone.dashboard.client;

import com.capitalone.dashboard.util.HygieiaRestConnection;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Supplier that returns an instance of RestOperations
 */
@Component
public class DefaultRestOperationsSupplier implements RestOperationsSupplier {

    public RestOperations get() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Integer.getInteger(HygieiaRestConnection.REST_CONNECT_TIMEOUT, 5000));
        requestFactory.setReadTimeout(Integer.getInteger(HygieiaRestConnection.REST_READ_TIMEOUT, 60000));
        return new RestTemplate(requestFactory);
    }
}