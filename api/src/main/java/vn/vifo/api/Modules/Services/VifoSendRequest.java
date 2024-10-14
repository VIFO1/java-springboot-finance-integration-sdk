package vn.vifo.api.Modules.Services;

import java.util.Map;

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
    public Map<String, Object> sendRequest(String method, String endpoint, Map<String, String> headers,
            Map<String, Object> body) {
        String url = this.baseUrl + endpoint;
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);

        try {
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.valueOf(method.toUpperCase()),
                    httpEntity,
                    Object.class);

            return Map.of(
                    "status_code", responseEntity.getStatusCode(),
                    "body", responseEntity.getBody(),
                    "http_code", responseEntity.getStatusCodeValue());
        } catch (HttpServerErrorException e) {
            return Map.of(
                    "errors", e.getMessage(),
                    "status_code", e.getStatusCode(),
                    "body", e.getResponseBodyAsString());
        } catch (HttpClientErrorException e) {
            return Map.of(
                    "errors", e.getMessage(),
                    "status_code", e.getStatusCode(),
                    "body", e.getResponseBodyAsString());
        }
    }
}