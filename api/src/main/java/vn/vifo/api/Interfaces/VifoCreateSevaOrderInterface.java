package vn.vifo.api.Interfaces;

import java.util.HashMap;

import vn.vifo.api.Modules.DTO.CreateSevaOrderResponse;

public interface VifoCreateSevaOrderInterface {

    public CreateSevaOrderResponse createSevaOrder(HashMap<String, String> headers, HashMap<String, Object> body);
}
