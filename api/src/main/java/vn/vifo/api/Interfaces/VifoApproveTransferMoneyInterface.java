package vn.vifo.api.Interfaces;
import java.util.Map;

public interface VifoApproveTransferMoneyInterface {
    public String createSignature(Map<String, Object> body, String secretKey, String timestamp);
    public Map<String, Object> approveTransfers(String secretKey, String timestamp, Map<String, String> headers,
                Map<String, Object> body);
}
