package vn.vifo.api.Interfaces;

import java.util.HashMap;

import vn.vifo.api.Modules.DTO.ApproveTransferMoneyResponse;
import vn.vifo.api.Modules.DTO.AuthenticateResponse;
import vn.vifo.api.Modules.DTO.BankResponse;
import vn.vifo.api.Modules.DTO.BeneficiaryNameResponse;
import vn.vifo.api.Modules.DTO.CreateRevaOrderResponse;
import vn.vifo.api.Modules.DTO.CreateSevaOrderResponse;
import vn.vifo.api.Modules.DTO.OtherRequestResponse;
import vn.vifo.api.Modules.DTO.TransferMoneyResponse;
import vn.vifo.api.Modules.DTO.WebhookResponse;

public interface VifoServiceFactoryInterface {

        public void setUserToken(String token);

        public void setAdminToken(String token);

        public HashMap<String, String> getAuthorizationHeaders(String type);

        public AuthenticateResponse performUserAuthentication(String username, String password);

        public BankResponse fetchBankInformation();

        public BeneficiaryNameResponse fetchBeneficiaryName(HashMap<String, Object> body);

        public TransferMoneyResponse executeMoneyTransfer(HashMap<String, Object> body);

        public ApproveTransferMoneyResponse approveMoneyTransfer(String secretKey, String timestamp,
                        HashMap<String, Object> body);

        public OtherRequestResponse processOtherRequest(String key);

        public WebhookResponse convertObjectToWebhookResponse(HashMap<String, Object> response);

        public boolean verifyWebhookSignature(HashMap<String, Object> data, String requestSignature, String secretKey,
                        String timestamp);

        public CreateRevaOrderResponse createRevaOrder(
                        String fullname,
                        String benefiaryAccountName,
                        String productCode,
                        String distributorOrderNumber,
                        String phone,
                        String email,
                        String address,
                        int finalAmount,
                        String comment,
                        boolean bankDetail,
                        QRTypeOrder qrType,
                        String endDate);

        public CreateSevaOrderResponse createSevaOrder(
                        String productCode,
                        String phone,
                        String fullname,
                        int finalAmount,
                        String distributorOrderNumber,
                        String beneficiaryBankCode,
                        String beneficiaryAccountNo,
                        String comment,
                        String sourceAccountNo
                        );
}
