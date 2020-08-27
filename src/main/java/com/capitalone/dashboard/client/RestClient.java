package com.capitalone.dashboard.client;

import com.capitalone.dashboard.util.Encryption;
import com.capitalone.dashboard.util.EncryptionException;
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
import org.springframework.http.MediaType;
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
    public RestClient(RestOperationsSupplier restOperationsSupplier) {
        this.restOperations = restOperationsSupplier.get();
    }

    /**
     * The most general version of POST call
     * @param url
     * @param httpEntity HTTP headers and body
     * @return
     */
    private <T> ResponseEntity<String> makeRestCallPost(String url, HttpEntity<T> httpEntity) {

        long start = System.currentTimeMillis();
        ResponseEntity<String> response;
        String status = null;
        try {
            response = restOperations.exchange(url, HttpMethod.POST, httpEntity, String.class);
            status = response.getStatusCode().toString();
        } catch (HttpStatusCodeException e) {
            status = e.getStatusCode().toString();
            LOG.info("status=" + status + ", requestBody=" + httpEntity.getBody());
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            LOG.info("makeRestCall op=POST url=" + url + ", status=" + status + " duration=" + (end - start));
        }

        return response;
    }

    /**
     * The general POST call taking a JSONObject as the body
     * @param url
     * @param headers HTTP headers, can be null
     * @param body Cannot be null
     * @return
     */
    public ResponseEntity<String> makeRestCallPost(String url, HttpHeaders headers, JSONObject body) {
	if (headers==null) headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity httpEntity = new HttpEntity<Object>(body, headers);
	return this.makeRestCallPost(url, httpEntity);
    }

    /**
     * The general POST call taking a String as the body, for non-json data
     * @param url
     * @param headers HTTP headers, can be null
     * @param body Cannot be null
     * @return
     */
    public ResponseEntity<String> makeRestCallPost(String url, HttpHeaders headers, String body) {
	HttpEntity httpEntity = new HttpEntity<Object>(body, headers);
	return this.makeRestCallPost(url, httpEntity);
    }

    /**
     * Make a POST call with no HTTP headers and a Json body
     *
     * @param url
     * @param body Cannot be null
     * @return
     */
    public ResponseEntity<String> makeRestCallPost(String url, JSONObject body) {
        if (restOperations == null) { return null; }

        return this.makeRestCallPost(url, (HttpHeaders)null, body);
    }

    /**
     * Make a POST call with a single header, Authorization, which has the value "[headerKey] [token]".
     * E.g. Authorization: token xxxxxxxxxxx
     * When either headerKey or token is null, no header is added
     * @param url
     * @param headerKey
     * @param token
     * @param body
     * @return
     */
    public ResponseEntity<String> makeRestCallPost(String url, String headerKey, String token, JSONObject body) {
        if (restOperations == null) { return null; }

        HttpHeaders headers = null;
        if (StringUtils.isNotEmpty(headerKey) && StringUtils.isNotEmpty(token)) {
            headers = createHeaders(headerKey, token);
        }

        return this.makeRestCallPost(url, headers, body);
    }

    /**
     * Make a POST call with a single header, Authorization, which has the value "Basic " plus base64 encoded userId:passCode.
     * E.g. Authorization: Basic base64EncodedUserIdAndPassCode
     * When userInfo is null, no header is added
     * @param url
     * @param userInfo
     * @param body
     * @return
     */
    public ResponseEntity<String> makeRestCallPost(String url, RestUserInfo userInfo, JSONObject body) {
        if (restOperations == null) { return null; }

        HttpHeaders headers = null;
        if ((userInfo != null)) {
            headers = createHeaders(userInfo.getFormattedString());
        }

        return this.makeRestCallPost(url, headers, body);
    }

    /**
     * The most general form of GET calls.
     * @param url
     * @param headers Can be null if no header is needed
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<String> makeRestCallGet(String url, HttpHeaders headers) throws RestClientException {

        long start = System.currentTimeMillis();
        ResponseEntity<String> response;
        String status = null;
        try {
            HttpEntity entity = headers==null?null:new HttpEntity(headers);
            response = restOperations.exchange(url, HttpMethod.GET, entity, String.class);
            status = response.getStatusCode().toString();
        } catch (HttpStatusCodeException e) {
            status = e.getStatusCode().toString();
            throw e;
        } catch (Exception e) {
            status = e.getClass().getCanonicalName();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            LOG.info("makeRestCall op=GET url=" + url + " status=" + status + " duration=" + (end - start));
        }
        return response;
    }


    /**
     * Make a GET call without headers
     * @param url
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<String> makeRestCallGet(String url) throws RestClientException {
        if (restOperations == null) { return null; }

        return this.makeRestCallGet(url, (HttpHeaders)null);
    }

    /**
     * Make a GET call with a single header, Authorization, which has the value "[headerKey] [token]".
     * E.g. Authorization: token xxxxxxxxxxx
     * When either headerKey or token is null, no header is added
     * @param url
     * @param headerKey
     * @param token
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<String> makeRestCallGet(String url, String headerKey, String token) throws RestClientException {
        if (restOperations == null) { return null; }

        HttpHeaders headers = null;
        if (StringUtils.isNotEmpty(headerKey) && StringUtils.isNotEmpty(token)) {
            headers = createHeaders(headerKey, token);
        }

        return this.makeRestCallGet(url, headers);

    }

    /**
     * Make a GET call with a single header, Authorization, which has the value "Basic " plus base64 encoded userId:passCode.
     * E.g. Authorization: Basic base64EncodedUserIdAndPassCode
     * When userInfo is null, no header is added
     * @param url
     * @param userInfo
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<String> makeRestCallGet(String url, RestUserInfo userInfo) throws RestClientException {
        if (restOperations == null) { return null; }

        HttpHeaders headers = null;
        if (userInfo != null && StringUtils.isNotEmpty(userInfo.getFormattedString())) {
            headers = createHeaders(userInfo.getFormattedString());
        }
        return this.makeRestCallGet(url, headers);
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
