package vn.vifo.api.Interfaces;

import java.util.Map;

import vn.vifo.api.Modules.Converters.WebhookResponse;

public interface WebhookInterface {

    public String createSignature(Map<String, Object> body, String secretKey, String timestamp);

    public boolean handleSignature(Map<String, Object> data, String requestSignature, String secretKey,
            String timestamp);

    public WebhookResponse convertObject(Map<String, Object> response);
}
