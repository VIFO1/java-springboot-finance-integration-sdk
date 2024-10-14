package vn.vifo.api.Interfaces;

import java.util.Map;
import java.util.List;

import vn.vifo.api.Modules.Converters.CreateRevaOrderResponse;

public interface VifoCreateRevaOrderInterface {

    public List<String> validateRequiredFields(String fullname, String distributorOrderNumber, String phone,
            int finalAmount);

    public CreateRevaOrderResponse createRevaOrder(Map<String, String> headers, Map<String, Object> body);
}
