package com.capitalone.dashboard.client;

import com.capitalone.dashboard.util.Supplier;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Supplier that returns an instance of RestOperations
 */
@Component
public class RestOperationsSupplier {

    public RestOperations get(RestClientSettings settings) {
        if (settings==null) {
            return new RestTemplate();
        } else {
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(settings.getRequestConnectTimeout());
            requestFactory.setReadTimeout(settings.getRequestReadTimeout());
            return new RestTemplate(requestFactory);
        }
    }
}
