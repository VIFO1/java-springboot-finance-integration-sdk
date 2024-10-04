package vn.vifo.api.Interfaces;
import java.util.List;
import java.util.Map;

public interface VifoBankInterface {
    public List<String> validateBody(Map<String, String> headers, Map<String, Object> body);
    public Map<String, Object> getBank(Map<String, String> headers);
    public Map<String, Object> getBeneficiaryName(Map<String, String> headers,Map<String, Object> body);
    
} 
