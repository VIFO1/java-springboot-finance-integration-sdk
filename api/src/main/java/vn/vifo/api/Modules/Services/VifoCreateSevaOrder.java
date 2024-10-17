package vn.vifo.api.Modules.Services;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoCreateSevaOrderInterface;
import vn.vifo.api.Modules.DTO.CreateSevaOrderResponse;
import vn.vifo.api.Ultils.HashingUtils;
import vn.vifo.api.Ultils.HttpStatusUtils;
import vn.vifo.api.Ultils.JsonParserUtils;

public class VifoCreateSevaOrder implements VifoCreateSevaOrderInterface {
    private VifoSendRequest sendRequest;
    private HashingUtils hashingUtils;
    private ObjectMapper objectMapper;

    public VifoCreateSevaOrder(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.hashingUtils = new HashingUtils();
        this.objectMapper = new ObjectMapper();
    }

    public CreateSevaOrderResponse createSevaOrder(HashMap<String, String> headers, HashMap<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = this.hashingUtils.validateCreateOrder(headers, body);
        if (!errors.isEmpty()) {
            return CreateSevaOrderResponse.builder()
                    .errors(String.join(",", errors))
                    .build();
        }
        try {
            HashMap<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            String errorsResponse = (String) apiResponse.get("errors");
            if (!statusCode.equals(HttpStatus.CREATED)) {
                return CreateSevaOrderResponse.builder()
                        .statusCode(HttpStatusUtils.getStatusMessage(statusCode))
                        .errors(errorsResponse)
                        .build();
            }
            String jsonResponse = JsonParserUtils.stringify(apiResponse);
            CreateSevaOrderResponse result = objectMapper.readValue(jsonResponse, CreateSevaOrderResponse.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
