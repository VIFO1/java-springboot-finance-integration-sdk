package vn.vifo.api.Interfaces;

import java.util.List;
import java.util.Map;

public interface VifoOtherRequestInterface {
    public List<String> validateOrderKey(Map<String, String> headers, String key);

    public Map<String, Object> checkOrderStatus(Map<String, String> headers, String key);

}