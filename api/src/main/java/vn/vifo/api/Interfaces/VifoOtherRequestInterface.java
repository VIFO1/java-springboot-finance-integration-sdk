package vn.vifo.api.Interfaces;

import java.util.List;
import java.util.Map;

import vn.vifo.api.Modules.Converters.OtherRequestResponse;

public interface VifoOtherRequestInterface {
    public List<String> validateOrderKey(Map<String, String> headers, String key);

    public OtherRequestResponse checkOrderStatus(Map<String, String> headers, String key);

}