package vn.vifo.api.Modules.Services;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import vn.vifo.api.Interfaces.VifoSendRequestInterface;

public class VifoSendRequest implements VifoSendRequestInterface {
    private String baseUrl;
    private RestTemplate restTemplate;

    public VifoSendRequest(String apiUrl) {
        this.baseUrl = apiUrl;
        this.restTemplate = new RestTemplate();
    }

    @SuppressWarnings("deprecation")
    public HashMap<String, Object> sendRequest(String method, String endpoint, HashMap<String, String> headers,
            HashMap<String, Object> body) {
        String url = this.baseUrl + endpoint;
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        HttpEntity<HashMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.valueOf(method.toUpperCase()),
                    httpEntity,
                    Object.class);

            responseMap.put("status_code", responseEntity.getStatusCode());
            responseMap.put("body", responseEntity.getBody());
            responseMap.put("http_code", responseEntity.getStatusCodeValue());
        } catch (HttpServerErrorException e) {
            responseMap.put("errors", e.getMessage());
            responseMap.put("status_code", e.getStatusCode());
            responseMap.put("body", e.getResponseBodyAsString());

        } catch (HttpClientErrorException e) {
            responseMap.put("errors", e.getMessage());
            responseMap.put("status_code", e.getStatusCode());
            responseMap.put("body", e.getResponseBodyAsString());
        }
        return responseMap;
    }
}