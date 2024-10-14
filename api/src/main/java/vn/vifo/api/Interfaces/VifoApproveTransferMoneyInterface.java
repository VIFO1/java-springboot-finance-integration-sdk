package vn.vifo.api.Interfaces;

import java.util.Map;

import vn.vifo.api.Modules.Converters.ApproveTransferMoneyResponse;

public interface VifoApproveTransferMoneyInterface {
    public String createSignature(Map<String, Object> body, String secretKey, String timestamp);

    public ApproveTransferMoneyResponse approveTransfers(String secretKey, String timestamp,
            Map<String, String> headers,
            Map<String, Object> body);
}
