package vn.vifo.api.Modules.Services;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoTransferMoneyInterface;
import vn.vifo.api.Modules.Converters.TransferMoneyResponse;

import java.util.List;
import java.util.ArrayList;

public class VifoTransferMoney implements VifoTransferMoneyInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoTransferMoney(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();

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

    public TransferMoneyResponse createTransferMoney(Map<String, String> headers, Map<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = validateRequestInput(headers, body);
        if (!errors.isEmpty()) {
            return TransferMoneyResponse.builder()
                    .errors(String.join(",", errors))
                    .build();
        }
        try {
            Map<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            if (statusCode == null || !statusCode.equals((HttpStatus.CREATED))) {
                String errorMessage = (String) apiResponse.get("errors");
                return TransferMoneyResponse.builder()
                        .statusCode(statusCode)
                        .errors(errorMessage)
                        .build();
            }
            String jsonResponse = objectMapper.writeValueAsString(apiResponse);
            TransferMoneyResponse transferMoneyResponse = objectMapper.readValue(jsonResponse,
                    TransferMoneyResponse.class);
            return transferMoneyResponse;
        } catch (Exception e) {
            return null;
        }
    }

}
