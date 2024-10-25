package vn.vifo.api.Interfaces;

import java.util.HashMap;
import java.util.List;

import vn.vifo.api.Modules.DTO.CreateSevaOrderResponse;

public interface VifoCreateSevaOrderInterface {
    public List<String> validateSevaOrder(HashMap<String, String> headers, HashMap<String, Object> body);
    public CreateSevaOrderResponse createSevaOrder(HashMap<String, String> headers, HashMap<String, Object> body);
}
