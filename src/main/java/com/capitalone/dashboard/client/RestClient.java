package com.capitalone.dashboard.client;

import com.capitalone.dashboard.util.Encryption;
import com.capitalone.dashboard.util.EncryptionException;
import com.capitalone.dashboard.util.Supplier;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class RestClient {
    private static final Log LOG = LogFactory.getLog(RestClient.class);
    private final RestOperations restOperations;

    @Autowired
    public RestClient(Supplier<RestOperations> restOperationsSupplier) {
        this.restOperations = restOperationsSupplier.get();
    }

    private ResponseEntity<String> makeRestCallPost(String url, HttpHeaders headers, JSONObject body) {

        long start = System.currentTimeMillis();
        ResponseEntity<String> response;
        HttpStatus status = null;
        try {
            response = restOperations.exchange(url, HttpMethod.POST, new HttpEntity<Object>(body, headers), String.class);
            status = response.getStatusCode();
        } catch (HttpStatusCodeException e) {
            status = e.getStatusCode();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            LOG.info("makeRestCall op=POST url=" + url + ", status=" + status + " duration=" + (end - start));
        }

        return response;
    }

    public ResponseEntity<String> makeRestCallPost(String url, JSONObject body) {
        if (restOperations == null) { return null; }

        return this.makeRestCallPost(url, (HttpHeaders)null, body);
    }

    public ResponseEntity<String> makeRestCallPost(String url, String headerKey, String token, JSONObject body) {
        if (restOperations == null) { return null; }

        HttpHeaders headers = null;
        if (StringUtils.isNotEmpty(headerKey) && StringUtils.isNotEmpty(token)) {
            headers = createHeaders(headerKey, token);
        }

        return this.makeRestCallPost(url, headers, body);
    }

    public ResponseEntity<String> makeRestCallPost(String url, RestUserInfo userInfo, JSONObject body) {
        if (restOperations == null) { return null; }

        HttpHeaders headers = null;
        if ((userInfo != null)) {
            headers = createHeaders(userInfo.getFormattedString());
        }

        return this.makeRestCallPost(url, headers, body);
    }

    private ResponseEntity<String> makeRestCallGet(String url, HttpEntity entity) throws RestClientException {

        long start = System.currentTimeMillis();
        ResponseEntity<String> response;
        HttpStatus status = null;
        try {
            response = restOperations.exchange(url, HttpMethod.GET, entity, String.class);
            status = response.getStatusCode();
        } catch (HttpStatusCodeException e) {
            status = e.getStatusCode();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            LOG.info("makeRestCall op=GET url=" + url + " status=" + status + " duration=" + (end - start));
        }
        return response;
    }

    public ResponseEntity<String> makeRestCallGet(String url) throws RestClientException {
        if (restOperations == null) { return null; }

        return this.makeRestCallGet(url, (HttpEntity)null);
    }

    public ResponseEntity<String> makeRestCallGet(String url, String headerKey, String token) throws RestClientException {
        if (restOperations == null) { return null; }

        HttpEntity entity = null;
        if (StringUtils.isNotEmpty(headerKey) && StringUtils.isNotEmpty(token)) {
            entity = new HttpEntity<>(createHeaders(headerKey, token));
        }

        return this.makeRestCallGet(url, entity);

    }

    public ResponseEntity<String> makeRestCallGet(String url, RestUserInfo userInfo) throws RestClientException {
        if (restOperations == null) { return null; }

        HttpEntity entity = null;
        if (userInfo != null && StringUtils.isNotEmpty(userInfo.getFormattedString())) {
            entity = new HttpEntity<>(createHeaders(userInfo.getFormattedString()));
        }
        return this.makeRestCallGet(url, entity);
    }

    protected HttpHeaders createHeaders(RestUserInfo restUserInfo) {
        return createHeaders(restUserInfo.getFormattedString());
    }

    protected HttpHeaders createHeaders(String user) {
        byte[] encodedAuth = Base64.encodeBase64(user.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return headers;
    }

    protected HttpHeaders createHeaders(String key, String token) {
        String authHeader = key.trim() + " " + token.trim();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return headers;
    }

    public JSONObject parseAsObject(ResponseEntity<String> response) throws ParseException {
        if (response == null) { return new JSONObject(); }

        return (JSONObject) new JSONParser().parse(response.getBody());
    }

    public JSONArray parseAsArray(ResponseEntity<String> response) throws ParseException {
        return (JSONArray) new JSONParser().parse(response.getBody());
    }

    public JSONArray getArray(JSONObject json, String key) {
        if (json == null) return new JSONArray();
        if (json.get(key) == null) return new JSONArray();
        return (JSONArray) json.get(key);
    }

    public String getString(Object obj, String key) {
        if (obj == null) return "";

        if (obj instanceof Map) {
            Map map = (Map) obj;
            Object value = map.get(key);
            return (value == null) ? "" : value.toString();
        } else if (obj instanceof JSONObject) {
            JSONObject json = (JSONObject) obj;
            Object value = json.get(key);
            return (value == null) ? "" : value.toString();
        }
        return "";
    }

    public Integer getInteger(Object obj, String key) throws NumberFormatException{
        return NumberUtils.toInt(getString(obj, key));
    }

    public Object getAsObject(Object obj, String key) {
        if (obj == null) return null;

        if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.get(key);
        } else if (obj instanceof JSONObject) {
            JSONObject json = (JSONObject) obj;
            return json.get(key);
        }

        return null;
    }

    public boolean getBoolean(Object obj, String key) {
        if (obj == null) return false;

        if (obj instanceof Map) {
            Map map = (Map) obj;
            return (Boolean) map.get(key);
        } else if (obj instanceof JSONObject) {
            JSONObject json = (JSONObject) obj;
            return (Boolean) json.get(key);
        }

        return false;
    }

    public Long getLong(Object obj, String key) throws NumberFormatException{
        return NumberUtils.toLong(getString(obj, key));
    }

    /**
     * Decrypt string
     *
     * @param string
     * @param key
     * @return String
     */
    public static String decryptString(String string, String key) {
        if (StringUtils.isEmpty(string)) { return ""; }

        String result = "";
        try {
            result = Encryption.decryptString(string, key);
        } catch (EncryptionException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }
}
