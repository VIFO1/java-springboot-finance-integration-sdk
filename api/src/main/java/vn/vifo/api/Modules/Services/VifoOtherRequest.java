package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoOtherRequestInterface;
import vn.vifo.api.Modules.Converters.OtherRequestResponse;

public class VifoOtherRequest implements VifoOtherRequestInterface{
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoOtherRequest(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();
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

    public OtherRequestResponse checkOrderStatus(Map<String, String> headers, String key) {
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
            Map<String, Object> apiRespone = this.sendRequest.sendRequest("GET", endpoint, headers, null);
            HttpStatus httpStatus = (HttpStatus) apiRespone.get("status_code");
            if (httpStatus == null || !httpStatus.equals(HttpStatus.OK)) {
                String errorMessage = (String) apiRespone.get("errors");
                return OtherRequestResponse.builder()
                        .statusCode(httpStatus)
                        .body(OtherRequestResponse.Body.builder()
                                .message(errorMessage)
                                .build())
                        .build();
            }

            String jsonResponse = objectMapper.writeValueAsString(apiRespone);
            OtherRequestResponse otherRequestResponse = objectMapper.readValue(jsonResponse,
                    OtherRequestResponse.class);
            return otherRequestResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
