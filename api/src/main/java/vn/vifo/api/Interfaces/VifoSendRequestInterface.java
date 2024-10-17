package vn.vifo.api.Interfaces;

import java.util.HashMap;

public interface VifoSendRequestInterface {
    public HashMap<String, Object> sendRequest(String method, String endpoint, HashMap<String, String> headers,
    HashMap<String, Object> body);

}