package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.Map;

import vn.vifo.api.CommonFunctions.CommonFunctions;
import vn.vifo.api.Interfaces.VifoApproveTransferMoneyInterface;

public class VifoApproveTransferMoney implements VifoApproveTransferMoneyInterface {
    private VifoSendRequest sendRequest;
    private CommonFunctions commonFunctions;

    public VifoApproveTransferMoney(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.commonFunctions = new CommonFunctions();
    }

    public String createSignature(Map<String, Object> body, String secretKey, String timestamp) {

        return this.commonFunctions.generateSignature(body, secretKey, timestamp);
    }

    public Map<String, Object> approveTransfers(String secretKey, String timestamp, Map<String, String> headers,
            Map<String, Object> body) {
        String endpoint = "/v2/finance/confirm";
        List<String> errors = this.commonFunctions.validateSignatureInputs(secretKey, timestamp, body);

        if (!errors.isEmpty()) {
            return Map.of("errors", errors);
        }
        return this.sendRequest.sendRequest("POST", endpoint, headers, body);
    }

}
