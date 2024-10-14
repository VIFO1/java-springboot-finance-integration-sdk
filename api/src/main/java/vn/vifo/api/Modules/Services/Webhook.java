package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.CommonFunctions.CommonFunctions;
import vn.vifo.api.Modules.Converters.WebhookResponse;
import vn.vifo.api.Interfaces.WebhookInterface;
public class Webhook implements WebhookInterface {
    private CommonFunctions commonFunctions;
    private ObjectMapper objectMapper;

    public Webhook() {
        this.commonFunctions = new CommonFunctions();
        this.objectMapper = new ObjectMapper();
    }

    public String createSignature(Map<String, Object> body, String secretKey, String timestamp) {

        return this.commonFunctions.generateSignature(body, secretKey, timestamp);
    }

    public boolean handleSignature(Map<String, Object> data, String requestSignature, String secretKey,
            String timestamp) {
        List<String> errors = this.commonFunctions.validateSignatureInputs(secretKey, timestamp, data);

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

    public WebhookResponse convertObject(Map<String,Object> response) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(response);
            return objectMapper.readValue(jsonResponse, WebhookResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
