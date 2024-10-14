package vn.vifo.api.Interfaces;

import java.util.Map;

import vn.vifo.api.Modules.Converters.ApproveTransferMoneyResponse;
import vn.vifo.api.Modules.Converters.AuthenticateResponse;
import vn.vifo.api.Modules.Converters.BankResponse;
import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse;
import vn.vifo.api.Modules.Converters.CreateRevaOrderResponse;
import vn.vifo.api.Modules.Converters.CreateSevaOrderResponse;
import vn.vifo.api.Modules.Converters.OtherRequestResponse;
import vn.vifo.api.Modules.Converters.TransferMoneyResponse;
import vn.vifo.api.Modules.Converters.WebhookResponse;

public interface VifoServiceFactoryInterface {

        public void setUserToken(String token);

        public void setAdminToken(String token);

        public Map<String, String> getAuthorizationHeaders(String type);

        public AuthenticateResponse performUserAuthentication(String username, String password);

        public BankResponse fetchBankInformation();

        public BeneficiaryNameResponse fetchBeneficiaryName(Map<String, Object> body);

        public TransferMoneyResponse executeMoneyTransfer(Map<String, Object> body);

        public ApproveTransferMoneyResponse approveMoneyTransfer(String secretKey, String timestamp,
                        Map<String, Object> body);

        public OtherRequestResponse processOtherRequest(String key);

        public WebhookResponse convertObjectToWebhookResponse(Map<String, Object> response);

        public boolean verifyWebhookSignature(Map<String, Object> data, String requestSignature, String secretKey,
                        String timestamp);

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

        );

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

        );
}
