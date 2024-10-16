package vn.vifo.api.Interfaces;

import vn.vifo.api.Modules.DTO.CreateRevaOrderResponse;

import java.util.HashMap;
import java.util.List;

public interface VifoCreateRevaOrderInterface {

    public List<String> validateRequiredFields(String fullname, String distributorOrderNumber, String phone,
            int finalAmount);

    public CreateRevaOrderResponse createRevaOrder(HashMap<String, String> headers, HashMap<String, Object> body);
}
