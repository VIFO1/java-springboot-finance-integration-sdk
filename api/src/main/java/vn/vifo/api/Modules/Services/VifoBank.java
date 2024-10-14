package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoBankInterface;
import vn.vifo.api.Modules.Converters.BankResponse;
import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse;
import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse.Body;

public class VifoBank implements VifoBankInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoBank(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();

    }

    public BankResponse getBank(Map<String, String> headers) {
        String endpoint = "/v2/data/banks/napas";

        if (headers == null || headers.isEmpty()) {
            return BankResponse.builder()
                    .body(BankResponse.Body.builder()
                            .success(false)
                            .message("Headers cannot be null or empty")
                            .build())
                    .build();
        }

        try {
            Map<String, Object> apiResponse = this.sendRequest.sendRequest("GET", endpoint, headers, null);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");

            if (statusCode == null || !statusCode.equals(HttpStatus.OK)) {
                String errorMessage = (String) apiResponse.get("errors");

                return BankResponse.builder()
                        .body(BankResponse.Body.builder()
                                .success(false)
                                .message(errorMessage)
                                .build())
                        .build();
            }

            String jsonResponse = objectMapper.writeValueAsString(apiResponse);
            BankResponse bankResponse = objectMapper.readValue(jsonResponse, BankResponse.class);
            return bankResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> validateBody(Map<String, String> headers, Map<String, Object> body) {
        List<String> errors = new ArrayList<>();
        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        }
        return errors;
    }

    public BeneficiaryNameResponse getBeneficiaryName(Map<String, String> headers, Map<String, Object> body) {
        String endpoint = "/v2/finance/napas/receiver";
        List<String> errors = validateBody(headers, body);

        if (!errors.isEmpty()) {
            return BeneficiaryNameResponse.builder()
                    .body(Body.builder()
                            .success(false)
                            .message(String.join(", ", errors))
                            .build())
                    .build();
        }

        try {
            Map<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");

            if (statusCode == null || !statusCode.equals(HttpStatus.OK)) {
                String errorMessage = (String) apiResponse.get("errors");

                return BeneficiaryNameResponse.builder()
                        .statusCode(statusCode)
                        .body(BeneficiaryNameResponse.Body.builder()
                                .success(false)
                                .message(errorMessage)
                                .build())
                        .build();
            }

            String jsonResponse = objectMapper.writeValueAsString(apiResponse);
            BeneficiaryNameResponse result = objectMapper.readValue(jsonResponse, BeneficiaryNameResponse.class);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
