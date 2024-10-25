package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoCreateRevaOrderInterface;
import vn.vifo.api.Modules.DTO.CreateRevaOrderResponse;
import vn.vifo.api.Ultils.HttpStatusUtils;
import vn.vifo.api.Ultils.JsonParserUtils;

public class VifoCreateRevaOrder implements VifoCreateRevaOrderInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoCreateRevaOrder(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();
    }

    public List<String> validateRevaOrder(HashMap<String, String> headers, HashMap<String, Object> body) {
        List<String> errors = new ArrayList<>();

        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        } else {
            String[] requiredFields = {
                    "product_code",
                    "distributor_order_number",
                    "phone",
                    "fullname",
                    "final_amount",
                    "benefiary_account_name",
                    "comment",
            };

            for (String field : requiredFields) {
                if (!body.containsKey(field) || body.get(field) == null
                        || body.get(field).toString().trim().isEmpty()) {
                    errors.add(field + " is required and cannot be empty.");
                }
            }
        }
        return errors;
    }

    public CreateRevaOrderResponse createRevaOrder(HashMap<String, String> headers, HashMap<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = this.validateRevaOrder(headers, body);
        if (!errors.isEmpty()) {
            return CreateRevaOrderResponse.builder()
                    .statusCode("400")
                    .errors(String.join(" ", errors))
                    .build();
        }
        try {
            HashMap<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            String errorsResponse = (String) apiResponse.get("errors");
            if (!statusCode.equals(HttpStatus.CREATED)) {
                return CreateRevaOrderResponse.builder()
                        .statusCode(HttpStatusUtils.getStatusMessage(statusCode))
                        .errors(errorsResponse)
                        .build();
            }
            String jsonResponse = JsonParserUtils.stringify(apiResponse);
            CreateRevaOrderResponse result = objectMapper.readValue(jsonResponse, CreateRevaOrderResponse.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
