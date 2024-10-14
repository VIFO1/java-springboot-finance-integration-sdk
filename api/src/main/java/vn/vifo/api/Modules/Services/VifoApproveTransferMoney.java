package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.CommonFunctions.CommonFunctions;
import vn.vifo.api.Interfaces.VifoApproveTransferMoneyInterface;
import vn.vifo.api.Modules.Converters.ApproveTransferMoneyResponse;

public class VifoApproveTransferMoney implements VifoApproveTransferMoneyInterface {
    private VifoSendRequest sendRequest;
    private CommonFunctions commonFunctions;
    private ObjectMapper objectMapper;

    public VifoApproveTransferMoney(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.commonFunctions = new CommonFunctions();
        this.objectMapper = new ObjectMapper();
    }

    public String createSignature(Map<String, Object> body, String secretKey, String timestamp) {

        return this.commonFunctions.generateSignature(body, secretKey, timestamp);
    }

    public ApproveTransferMoneyResponse approveTransfers(String secretKey, String timestamp,
            Map<String, String> headers,
            Map<String, Object> body) {
        String endpoint = "/v2/finance/confirm";
        List<String> errors = this.commonFunctions.validateSignatureInputs(secretKey, timestamp, body);

        if (!errors.isEmpty()) {
            return ApproveTransferMoneyResponse.builder()
                    .body(ApproveTransferMoneyResponse.Body.builder()
                            .message(String.join(",", errors))
                            .build())
                    .build();
        }

        try {
            Map<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            if (statusCode == null || !statusCode.equals(HttpStatus.OK)) {
                String message = (String) apiResponse.get("message");
                return ApproveTransferMoneyResponse.builder()
                        .statusCode(statusCode)
                        .body(ApproveTransferMoneyResponse.Body.builder()
                                .success(false)
                                .message(message)
                                .code(00)
                                .build())
                        .build();
            }

            String jsonResponse = objectMapper.writeValueAsString(apiResponse);
            ApproveTransferMoneyResponse approveTransferMoneyResponse = objectMapper.readValue(jsonResponse,
                    ApproveTransferMoneyResponse.class);
            return approveTransferMoneyResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
