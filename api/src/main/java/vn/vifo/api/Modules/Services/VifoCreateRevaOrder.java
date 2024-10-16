package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoCreateRevaOrderInterface;
import vn.vifo.api.Modules.DTO.CreateRevaOrderResponse;
import vn.vifo.api.Ultils.HashingUtils;

public class VifoCreateRevaOrder implements VifoCreateRevaOrderInterface {
    private VifoSendRequest sendRequest;
    private HashingUtils hashingUtils;
    private ObjectMapper objectMapper;

    public VifoCreateRevaOrder(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.hashingUtils = new HashingUtils();
        this.objectMapper = new ObjectMapper();
    }

    public List<String> validateRequiredFields(String fullname, String distributorOrderNumber, String phone,
            int finalAmount) {
        List<String> errors = new ArrayList<>();
        if (fullname == null || fullname.isEmpty()) {
            errors.add("fullname cannot be null or empty.");
        }
        if (distributorOrderNumber == null || distributorOrderNumber.isEmpty()) {
            errors.add("distributor_order_number cannot be null or empty.");
        }
        if (phone == null || phone.isEmpty()) {
            errors.add("phone cannot be null or empty.");
        }
        if (finalAmount <= 0) {
            errors.add("final_amount must be greater than 0.");
        }
        return errors;
    }

    public CreateRevaOrderResponse createRevaOrder(HashMap<String, String> headers, HashMap<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = this.hashingUtils.validateCreateOrder(headers, body);
        if (!errors.isEmpty()) {
            return CreateRevaOrderResponse.builder()
                    .errors(String.join(",", errors))
                    .build();
        }
        try {
            HashMap<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            String errorsResponse = (String) apiResponse.get("errors");
            if (!statusCode.equals(HttpStatus.CREATED)) {
                return CreateRevaOrderResponse.builder()
                        .statusCode(statusCode)
                        .errors(errorsResponse)
                        .build();
            }
            String jsonReponse = objectMapper.writeValueAsString(apiResponse);
            CreateRevaOrderResponse result = objectMapper.readValue(jsonReponse, CreateRevaOrderResponse.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
