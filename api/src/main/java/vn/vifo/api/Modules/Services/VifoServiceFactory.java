package vn.vifo.api.Modules.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import vn.vifo.api.Interfaces.QRTypeOrder;
import vn.vifo.api.Interfaces.VifoServiceFactoryInterface;
import vn.vifo.api.Modules.Converters.ApproveTransferMoneyResponse;
import vn.vifo.api.Modules.Converters.AuthenticateResponse;
import vn.vifo.api.Modules.Converters.BankResponse;
import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse;
import vn.vifo.api.Modules.Converters.CreateRevaOrderResponse;
import vn.vifo.api.Modules.Converters.CreateSevaOrderResponse;
import vn.vifo.api.Modules.Converters.OtherRequestResponse;
import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse.Body;
import vn.vifo.api.Modules.Converters.TransferMoneyResponse;
import vn.vifo.api.Modules.Converters.WebhookResponse;

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

    public AuthenticateResponse performUserAuthentication(String username, String password) {
        AuthenticateResponse response = authenticate.authenticateUser(this.headersLogin, username, password);
        if (response == null) {
            return AuthenticateResponse.builder()
                    .body(AuthenticateResponse.Body.builder()
                            .message(response != null ? response.getBody().getMessage() : "Unknown error")
                            .build())
                    .build();
        }

        return response;
    }

    public BankResponse fetchBankInformation() {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        BankResponse response = this.bank.getBank(headers);
        if (response == null) {
            return BankResponse.builder()
                    .body(BankResponse.Body.builder()
                            .message(response != null ? response.getBody().getMessage() : "Unknown error")
                            .build())
                    .build();
        }
        return response;
    }

    public BeneficiaryNameResponse fetchBeneficiaryName(Map<String, Object> body) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        if (!body.containsKey("bank_code") || !body.containsKey("account_number")) {
            BeneficiaryNameResponse responseBody = BeneficiaryNameResponse.builder()
                    .body(Body.builder()
                            .success(false)
                            .message("missing 'bank_code' or 'account_number")
                            .build())
                    .build();
            return responseBody;
        }
        return this.bank.getBeneficiaryName(headers, body);
    }

    public TransferMoneyResponse executeMoneyTransfer(Map<String, Object> body) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");

        TransferMoneyResponse response = this.transferMoney.createTransferMoney(headers, body);
        if (response.getBody() != null) {
            return TransferMoneyResponse.builder()
                    .statusCode(response.getStatusCode())
                    .body(response.getBody())
                    .build();
        }
        return response;
    }

    public ApproveTransferMoneyResponse approveMoneyTransfer(String secretKey, String timestamp,
            Map<String, Object> body) {
        Map<String, String> headers = this.getAuthorizationHeaders("admin");
        String requestSignature = this.approveTransferMoney.createSignature(body, secretKey, timestamp);
        System.out.println(requestSignature);
        headers.put("x-request-timestamp", timestamp);
        headers.put("x-request-signature", requestSignature);
        ApproveTransferMoneyResponse response = this.approveTransferMoney.approveTransfers(secretKey, timestamp,
                headers, body);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ApproveTransferMoneyResponse.builder()
                    .statusCode(response.getStatusCode())
                    .body(response.getBody())
                    .build();
        }
        return response;
    }

    public OtherRequestResponse processOtherRequest(String key) {
        Map<String, String> headers = this.getAuthorizationHeaders("user");
        OtherRequestResponse response = this.otherRequest.checkOrderStatus(headers, key);
        if (response.getStatusCode() == HttpStatus.OK) {
            return OtherRequestResponse.builder()
                    .statusCode(response.getStatusCode())
                    .body(response.getBody())
                    .build();
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

    public WebhookResponse convertObjectToWebhookResponse(Map<String, Object> response) {
        WebhookResponse result = this.webhook.convertObject(response);

        if (result.getActionName() == null || result.getData() == null) {
            return WebhookResponse.builder()
                    .errors("data conversion failed")
                    .build();
        }
        return result;
    }

    public CreateRevaOrderResponse createRevaOrder(
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

        List<String> errors = this.orderReva.validateRequiredFields(fullname, distributorOrderNumber, phone,
                finalAmount);
        if (!errors.isEmpty()) {
            return CreateRevaOrderResponse.builder()
                    .errors(String.join("", errors))
                    .build();
        }

        CreateRevaOrderResponse response = this.orderReva.createRevaOrder(headers, body);
        return response;
    }

    public CreateSevaOrderResponse createSevaOrder(
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
        CreateSevaOrderResponse response = this.orderSeva.createSevaOrder(headers, body);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return CreateSevaOrderResponse.builder()
                    .statusCode(response.getStatusCode())
                    .body(response.getBody())
                    .build();
        }
        return response;
    }

}
