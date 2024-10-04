package vn.vifo.api.Interfaces;

import java.util.Map;

interface WebhookInterface {

    public String createSignature(Map<String, Object> body, String secretKey, String timestamp);

    public boolean handleSignature(Map<String, Object> data, String requestSignature, String secretKey,
            String timestamp);
}
