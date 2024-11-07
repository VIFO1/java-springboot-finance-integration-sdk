package vn.vifo.api.Modules.Services;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoCreateSevaOrderInterface;
import vn.vifo.api.Modules.DTO.CreateSevaOrderResponse;
import vn.vifo.api.Ultils.HttpStatusUtils;
import vn.vifo.api.Ultils.JsonParserUtils;

public class VifoCreateSevaOrder implements VifoCreateSevaOrderInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoCreateSevaOrder(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();
    }

    public List<String> validateSevaOrder(HashMap<String, String> headers, HashMap<String, Object> body) {
        List<String> errors = new ArrayList<>();

        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        } else {
            String[] requiredFields = {
                    "product_code",
                    "phone",
                    "fullname",
                    "final_amount",
                    "distributor_order_number",
                    "benefiary_account_no",
                    "benefiary_bank_code",
                    "comment",
            };

            for (String field : requiredFields) {
                if (!body.containsKey(field) || body.get(field) == null || body.get(field).toString().trim().isEmpty()) {
                    errors.add(field + " is required and cannot be empty.");
                }
            }
        }
        return errors;
    }

    public CreateSevaOrderResponse createSevaOrder(HashMap<String, String> headers, HashMap<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = this.validateSevaOrder(headers, body);
        if (!errors.isEmpty()) {
            return CreateSevaOrderResponse.builder()
                    .statusCode("400")
                    .errors(String.join("", errors))
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
