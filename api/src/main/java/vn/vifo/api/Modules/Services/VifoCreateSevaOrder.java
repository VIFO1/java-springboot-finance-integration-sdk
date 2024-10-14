package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.CommonFunctions.CommonFunctions;
import vn.vifo.api.Interfaces.VifoCreateSevaOrderInterface;
import vn.vifo.api.Modules.Converters.CreateSevaOrderResponse;

public class VifoCreateSevaOrder implements VifoCreateSevaOrderInterface {
    private VifoSendRequest sendRequest;
    private CommonFunctions commonFunctions;
    private ObjectMapper objectMapper;

    public VifoCreateSevaOrder(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.commonFunctions = new CommonFunctions();
        this.objectMapper = new ObjectMapper();
    }

    public CreateSevaOrderResponse createSevaOrder(Map<String, String> headers, Map<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = this.commonFunctions.validateCreateOrder(headers, body);
        if (!errors.isEmpty()) {
            return CreateSevaOrderResponse.builder()
                    .errors(String.join(",", errors))
                    .build();
        }
        try {
            Map<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            String errorsResponse = (String) apiResponse.get("errors");
            if (!statusCode.equals(HttpStatus.CREATED)) {
                return CreateSevaOrderResponse.builder()
                        .statusCode(statusCode)
                        .errors(errorsResponse)
                        .build();
            }
            String jsonReponse = objectMapper.writeValueAsString(apiResponse);
            CreateSevaOrderResponse result = objectMapper.readValue(jsonReponse, CreateSevaOrderResponse.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
