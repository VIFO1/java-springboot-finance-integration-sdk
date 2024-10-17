package vn.vifo.api.Modules.Services;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Ultils.HashingUtils;
import vn.vifo.api.Ultils.JsonParserUtils;
import vn.vifo.api.Interfaces.WebhookInterface;
import vn.vifo.api.Modules.DTO.WebhookResponse;

public class Webhook implements WebhookInterface {
    private HashingUtils hashingUtils;
    private ObjectMapper objectMapper;

    public Webhook() {
        this.hashingUtils = new HashingUtils();
        this.objectMapper = new ObjectMapper();
    }

    public String createSignature(HashMap<String, Object> body, String secretKey, String timestamp) {

        return this.hashingUtils.generateSignature(body, secretKey, timestamp);
    }

    public boolean handleSignature(HashMap<String, Object> data, String requestSignature, String secretKey,
            String timestamp) {
        List<String> errors = this.hashingUtils.validateSignatureInputs(secretKey, timestamp, data);

        if (!errors.isEmpty()) {
            return false;
        }

        String generatedSignature = createSignature(data, secretKey, timestamp);
        if (generatedSignature.equals(requestSignature)) {
            return true;
        } else {
            return false;
        }
    }

    public WebhookResponse convertObject(HashMap<String, Object> response) {
        try {
            String jsonResponse = JsonParserUtils.stringify(response);
            return objectMapper.readValue(jsonResponse, WebhookResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
