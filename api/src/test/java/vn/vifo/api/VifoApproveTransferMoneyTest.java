package vn.vifo.api;

import java.util.HashMap;
import java.util.Map;

import vn.vifo.api.Modules.DTO.ApproveTransferMoneyResponse;
import vn.vifo.api.Modules.Services.VifoApproveTransferMoney;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoApproveTransferMoneyTest {
    public static void main(String[] args)
    {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoApproveTransferMoney approveTransferMoney = new VifoApproveTransferMoney(sendRequest);
        String secretKey = "";
        String timestamp = "2023-11-17 10:00:00";
        String[] ids = new String[] {"xeml73oexmm5d9qb"}; 
        HashMap<String, Object> body = null;
        HashMap<String, String> headers = null;
        ApproveTransferMoneyResponse test = approveTransferMoney.approveTransfers(secretKey,timestamp,headers,body);
        String createSignatureTest  = approveTransferMoney.createSignature(body, secretKey, timestamp);
        System.out.println(test);
        System.out.println(createSignatureTest);
    }
}
