package vn.vifo.api.Interfaces;

import java.util.HashMap;

import vn.vifo.api.Modules.DTO.WebhookResponse;

public interface WebhookInterface {

    public String createSignature(HashMap<String, Object> body, String secretKey, String timestamp);

    public boolean handleSignature(HashMap<String, Object> data, String requestSignature, String secretKey,
            String timestamp);

    public WebhookResponse convertObject(HashMap<String, Object> response);
}
