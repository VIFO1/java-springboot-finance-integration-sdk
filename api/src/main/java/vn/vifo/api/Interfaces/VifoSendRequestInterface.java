package vn.vifo.api.Interfaces;

import java.util.Map;

public interface VifoSendRequestInterface {
    public Map<String, Object> sendRequest(String method, String endpoint, Map<String, String> headers,
            Map<String, Object> body);

}