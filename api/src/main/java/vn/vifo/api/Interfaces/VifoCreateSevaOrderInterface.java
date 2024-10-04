package vn.vifo.api.Interfaces;

import java.util.Map;


public interface VifoCreateSevaOrderInterface {

    public Map<String, Object> createSevaOrder(Map<String, String> headers, Map<String, Object> body);
}
