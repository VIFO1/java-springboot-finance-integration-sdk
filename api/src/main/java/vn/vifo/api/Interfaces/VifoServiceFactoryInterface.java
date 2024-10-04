package vn.vifo.api.Interfaces;

import java.util.Map;

public interface VifoServiceFactoryInterface {

        public void setUserToken(String token);

        public void setAdminToken(String token);

        public Map<String, String> getAuthorizationHeaders(String type);

        public Map<String, Object> performUserAuthentication(String username, String password);

        public Map<String, Object> fetchBankInformation();

        public Map<String, Object> fetchBeneficiaryName(Map<String, Object> body);

        public Map<String, Object> executeMoneyTransfer(Map<String, Object> body);

        public Map<String, Object> approveMoneyTransfer(String secretKey, String timestamp, Map<String, Object> body);

        public Map<String, Object> processOtherRequest(String key);

        public boolean verifyWebhookSignature(Map<String, Object> data, String requestSignature, String secretKey,
                        String timestamp);

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

        );

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

        );
}
