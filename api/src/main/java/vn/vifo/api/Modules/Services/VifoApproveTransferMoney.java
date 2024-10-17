package vn.vifo.api.Modules.Services;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoApproveTransferMoneyInterface;
import vn.vifo.api.Modules.DTO.ApproveTransferMoneyResponse;
import vn.vifo.api.Ultils.HashingUtils;
import vn.vifo.api.Ultils.HttpStatusUtils;
import vn.vifo.api.Ultils.JsonParserUtils;

public class VifoApproveTransferMoney implements VifoApproveTransferMoneyInterface {
    private VifoSendRequest sendRequest;
    private HashingUtils hashingUtils;
    private ObjectMapper objectMapper;

    public VifoApproveTransferMoney(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.hashingUtils = new HashingUtils();
        this.objectMapper = new ObjectMapper();
    }

    public String createSignature(HashMap<String, Object> body, String secretKey, String timestamp) {

        return this.hashingUtils.generateSignature(body, secretKey, timestamp);
    }

    public ApproveTransferMoneyResponse approveTransfers(String secretKey, String timestamp,
            HashMap<String, String> headers,
            HashMap<String, Object> body) {
        String endpoint = "/v2/finance/confirm";
        List<String> errors = this.hashingUtils.validateSignatureInputs(secretKey, timestamp, body);

        if (!errors.isEmpty()) {
            return ApproveTransferMoneyResponse.builder()
                    .body(ApproveTransferMoneyResponse.Body.builder()
                            .message(String.join(",", errors))
                            .build())
                    .build();
        }

        try {
            HashMap<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);
            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            if (statusCode == null || !statusCode.equals(HttpStatus.OK)) {
                String message = (String) apiResponse.get("message");
                return ApproveTransferMoneyResponse.builder()
                        .statusCode(HttpStatusUtils.getStatusMessage(statusCode))
                        .body(ApproveTransferMoneyResponse.Body.builder()
                                .success(false)
                                .message(message)
                                .code(00)
                                .build())
                        .build();
            }

            String jsonResponse = JsonParserUtils.stringify(apiResponse);
            ApproveTransferMoneyResponse approveTransferMoneyResponse = objectMapper.readValue(jsonResponse,
                    ApproveTransferMoneyResponse.class);
            return approveTransferMoneyResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
