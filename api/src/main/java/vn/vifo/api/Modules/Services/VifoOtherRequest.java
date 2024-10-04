package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.vifo.api.Interfaces.VifoOtherRequestInterface;

public class VifoOtherRequest implements VifoOtherRequestInterface {
    private VifoSendRequest sendRequest;

    public VifoOtherRequest(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
    }

    public List<String> validateOrderKey(Map<String, String> headers, String key) {
        List<String> errors = new ArrayList<>();
        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (key == null || key.isEmpty()) {
            errors.add("key must not be empty");
        }
        return errors;
    }

    public Map<String, Object> checkOrderStatus(Map<String, String> headers, String key) {
        String endpoint = "/v2/finance/" + key + "/status";
        List<String> errors = validateOrderKey(headers, key);
        if (!errors.isEmpty()) {
            return Map.of("errors", errors);
        }
        return this.sendRequest.sendRequest("GET", endpoint, headers, null);
    }
}
