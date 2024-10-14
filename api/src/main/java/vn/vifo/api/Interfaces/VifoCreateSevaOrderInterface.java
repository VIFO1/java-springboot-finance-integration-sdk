package vn.vifo.api.Interfaces;

import java.util.Map;

import vn.vifo.api.Modules.Converters.CreateSevaOrderResponse;

public interface VifoCreateSevaOrderInterface {

    public CreateSevaOrderResponse createSevaOrder(Map<String, String> headers, Map<String, Object> body);
}
