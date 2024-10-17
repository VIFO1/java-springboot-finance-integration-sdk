package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoOtherRequestInterface;
import vn.vifo.api.Modules.DTO.OtherRequestResponse;
import vn.vifo.api.Ultils.HttpStatusUtils;
import vn.vifo.api.Ultils.JsonParserUtils;

public class VifoOtherRequest implements VifoOtherRequestInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoOtherRequest(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();
    }

    public List<String> validateOrderKey(HashMap<String, String> headers, String key) {
        List<String> errors = new ArrayList<>();
        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (key == null || key.isEmpty()) {
            errors.add("key must not be empty");
        }
        return errors;
    }

    public OtherRequestResponse checkOrderStatus(HashMap<String, String> headers, String key) {
        String endpoint = "/v2/finance/" + key + "/status";
        List<String> errors = validateOrderKey(headers, key);
        if (!errors.isEmpty()) {
            return OtherRequestResponse.builder()
                    .body(OtherRequestResponse.Body.builder()
                            .message(String.join(",", errors + ","))
                            .build())
                    .build();
        }
        try {
            HashMap<String, Object> apiResponse = this.sendRequest.sendRequest("GET", endpoint, headers, null);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            if (statusCode == null || !statusCode.equals(HttpStatus.OK)) {
                String errorMessage = (String) apiResponse.get("errors");
                return OtherRequestResponse.builder()
                        .statusCode(HttpStatusUtils.getStatusMessage(statusCode))
                        .body(OtherRequestResponse.Body.builder()
                                .message(errorMessage)
                                .build())
                        .build();
            }

            String jsonResponse = JsonParserUtils.stringify(apiResponse);
            OtherRequestResponse otherRequestResponse = objectMapper.readValue(jsonResponse,
                    OtherRequestResponse.class);
            return otherRequestResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
