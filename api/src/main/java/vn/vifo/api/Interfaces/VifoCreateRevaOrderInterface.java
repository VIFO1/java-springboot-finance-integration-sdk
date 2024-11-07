package vn.vifo.api.Interfaces;

import vn.vifo.api.Modules.DTO.CreateRevaOrderResponse;

import java.util.HashMap;
import java.util.List;

public interface VifoCreateRevaOrderInterface {

    public List<String> validateRevaOrder(HashMap<String, String> headers, HashMap<String, Object> body);

    public CreateRevaOrderResponse createRevaOrder(HashMap<String, String> headers, HashMap<String, Object> body);
}
