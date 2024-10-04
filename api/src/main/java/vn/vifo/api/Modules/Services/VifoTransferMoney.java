package vn.vifo.api.Modules.Services;

import java.util.Map;

import vn.vifo.api.Interfaces.VifoTransferMoneyInterface;

import java.util.List;
import java.util.ArrayList;

public class VifoTransferMoney implements VifoTransferMoneyInterface{
    private VifoSendRequest sendRequest;

    public VifoTransferMoney(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
    }

    public List<String> validateRequestInput(Map<String, String> headers, Map<String, Object> body) {
        List<String> errors = new ArrayList<>();
        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        }
        return errors;
    }

    public Map<String, Object> createTransferMoney(Map<String, String> headers, Map<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = validateRequestInput(headers, body);
        if (!errors.isEmpty()) {
            return Map.of("errors", errors);
        }

        return this.sendRequest.sendRequest("POST", endpoint, headers, body);
    }

}
