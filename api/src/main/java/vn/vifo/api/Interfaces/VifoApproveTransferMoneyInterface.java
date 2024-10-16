package vn.vifo.api.Interfaces;

import java.util.HashMap;

import vn.vifo.api.Modules.DTO.ApproveTransferMoneyResponse;

public interface VifoApproveTransferMoneyInterface {
    public String createSignature(HashMap<String, Object> body, String secretKey, String timestamp);

    public ApproveTransferMoneyResponse approveTransfers(String secretKey, String timestamp,
            HashMap<String, String> headers,
            HashMap<String, Object> body);
}
