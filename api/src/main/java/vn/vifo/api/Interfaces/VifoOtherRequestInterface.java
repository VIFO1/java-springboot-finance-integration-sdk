package vn.vifo.api.Interfaces;

import java.util.HashMap;
import java.util.List;

import vn.vifo.api.Modules.DTO.OtherRequestResponse;

public interface VifoOtherRequestInterface {
    public List<String> validateOrderKey(HashMap<String, String> headers, String key);

    public OtherRequestResponse checkOrderStatus(HashMap<String, String> headers, String key);

}