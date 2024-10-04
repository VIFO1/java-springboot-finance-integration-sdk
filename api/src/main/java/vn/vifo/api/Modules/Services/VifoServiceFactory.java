package vn.vifo.api.Modules.Services;

import java.util.HashMap;
import java.util.Map;
import vn.vifo.api.Interfaces.QRTypeOrder;
import vn.vifo.api.Interfaces.VifoServiceFactoryInterface;

public class VifoServiceFactory implements VifoServiceFactoryInterface {
    private VifoSendRequest sendRequest;
    private VifoAuthenticate authenticate;
    private Map<String, String> headersLogin;
    private HashMap<String, String> headers = new HashMap<>();
    private String userToken;
    private String adminToken;
    private VifoBank bank;
    private VifoTransferMoney transferMoney;
    private VifoApproveTransferMoney approveTransferMoney;
    private VifoOtherRequest otherRequest;
    private Webhook webhook;
    private VifoCreateRevaOrder orderReva;
    private VifoCreateSevaOrder orderSeva;

    public VifoServiceFactory(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.authenticate = new VifoAuthenticate(this.sendRequest);
        this.bank = new VifoBank(this.sendRequest);
        this.transferMoney = new VifoTransferMoney(this.sendRequest);
        this.approveTransferMoney = new VifoApproveTransferMoney(this.sendRequest);
        this.otherRequest = new VifoOtherRequest(this.sendRequest);
        this.webhook = new Webhook();
        this.orderReva = new VifoCreateRevaOrder(this.sendRequest);
        this.orderSeva = new VifoCreateSevaOrder(this.sendRequest);

        this.headersLogin = Map.of(
                "Accept", "application/json,text/plain,*/*",
                "Accept-Encoding", "gzip, deflate",
                "Accept-Language", "*");

        this.headers.put("Accept", "application/json");
        this.headers.put("Content-Type", "application/json");
        this.userToken = null;
        this.adminToken = null;
    }

    public void setUserToken(String token) {
        this.userToken = token;
    }

    public void setAdminToken(String token) {
        this.adminToken = token;
    }

    public Map<String, String> getAuthorizationHeaders(String type) {
        String token;
        if (type.equals("user")) {
            token = this.userToken;
        } else if (type.equals("admin")) {
            token = this.adminToken;
        } else {
            token = null;
        }
        this.headers.put("Authorization", "Bearer " + token);
        return new HashMap<>(this.headers);
    }

    public Map<String, Object> performUserAuthentication(String username, String password) {
        Map<String, Object> response = authenticate.authenticateUser(this.headersLogin, username, password);
        if (response.containsKey("errors")) {
            return Map.of(
                    "status", "errors",
                    "message", "Authentication failed",
                    "body", response.get("body"));
        }

        return response;
    }

    public Map<String, Object> fetchBankInformation() {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        Map<String, Object> response = this.bank.getBank(headers);

        if (response.containsKey("errors")) {
            return Map.of(
                    "status", "errors",
                    "message", response.get("errors"),
                    "status_code", response.get("status_code"));
        }
        return response;
    }

    public Map<String, Object> fetchBeneficiaryName(Map<String, Object> body) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        if (!body.containsKey("bank_code") || !body.containsKey("account_number")) {
            return Map.of("status", "errors", "message", "Required fields missing: bank_code or account_number");
        }
        return this.bank.getBeneficiaryName(headers, body);
    }

    public Map<String, Object> executeMoneyTransfer(Map<String, Object> body) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");

        Map<String, Object> response = this.transferMoney.createTransferMoney(headers, body);
        if (response.containsKey("errors")) {
            return Map.of(
                    "status", "errors",
                    "body", response.get("body"),
                    "status_code", response.get("status_code"),
                    "errors", response.get("errors"));
        }
        return response;
    }

    public Map<String, Object> approveMoneyTransfer(String secretKey, String timestamp, Map<String, Object> body) {
        Map<String, String> headers = this.getAuthorizationHeaders("admin");
        String requestSignature = this.approveTransferMoney.createSignature(body, secretKey, timestamp);
        headers.put("x-request-timestamp", timestamp);
        headers.put("x-request-signature", requestSignature);
        Map<String, Object> response = this.approveTransferMoney.approveTransfers(secretKey, timestamp, headers, body);

        if (response.containsKey("status_code")) {
            return Map.of(
                    "status_code", response.get("status_code"),
                    "body", response.get("body"));
        }
        return response;
    }

    public Map<String, Object> processOtherRequest(String key) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        Map<String, Object> response = this.otherRequest.checkOrderStatus(headers, key);
        if (response.containsKey("status_code")) {
            return Map.of(
                    "status_code", response.get("status_code"),
                    "body", response.get("body"),
                    "data", "");
        }
        return response;
    }

    public boolean verifyWebhookSignature(Map<String, Object> data, String requestSignature, String secretKey,
            String timestamp) {
        boolean result = this.webhook.handleSignature(data, requestSignature, secretKey, timestamp);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Object> createRevaOrder(
            String fullname,
            String beneficiaryBankCode,
            String beneficiaryAccountNo,
            String productCode,
            String distributorOrderNumber,
            String phone,
            String email,
            String address,
            int finalAmount,
            String comment,
            boolean bankDetail,
            QRTypeOrder qrType,
            String endDate

    ) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        Map<String, Object> body = new HashMap<>();
        String actualProductCodeReva = (productCode == null || productCode.isEmpty()) ? "REVAVF240101" : productCode;
        body.put("fullname", fullname);
        body.put("benefiary_bank_code", beneficiaryAccountNo);
        body.put("benefiary_account_no", beneficiaryAccountNo);
        body.put("product_code", actualProductCodeReva);
        body.put("distributor_order_number", distributorOrderNumber);
        body.put("phone", phone);
        body.put("email", email);
        body.put("address", address);
        body.put("final_amount", finalAmount);
        body.put("comment", comment);
        body.put("bank_detail", bankDetail);
        body.put("qr_type", qrType);
        body.put("end_date", endDate);
        Map<String, Object> response = this.orderReva.createRevaOrder(headers, body);
        if (response.containsKey("status_code")) {
            return Map.of(
                    "status_code", response.get("status_code"),
                    "body", response.get("body"));
        }
        return response;
    }

    public Map<String, Object> createSevaOrder(
            String fullname,
            String beneficiaryBankCode,
            String beneficiaryAccountNo,
            String productCode,
            String distributorOrderNumber,
            String phone,
            String email,
            String address,
            int finalAmount,
            String comment,
            boolean bankDetail,
            QRTypeOrder qrType,
            String endDate

    ) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        Map<String, Object> body = new HashMap<>();
        String actualProductCodeReva = (productCode == null || productCode.isEmpty()) ? "SEVAVF240101" : productCode;
        body.put("fullname", fullname);
        body.put("benefiary_bank_code", beneficiaryBankCode);
        body.put("benefiary_account_no", beneficiaryAccountNo);
        body.put("product_code", actualProductCodeReva);
        body.put("distributor_order_number", distributorOrderNumber);
        body.put("phone", phone);
        body.put("email", email);
        body.put("address", address);
        body.put("final_amount", finalAmount);
        body.put("comment", comment);
        body.put("bank_detail", bankDetail);
        body.put("qr_type", qrType);
        body.put("end_date", endDate);
        Map<String, Object> response = this.orderSeva.createSevaOrder(headers, body);
        if (response.containsKey("status_code")) {
            return Map.of(
                    "status_code", response.get("status_code"),
                    "body", response.get("body"));
        }
        return response;
    }

}
