package vn.vifo.api.Interfaces;

import java.util.Map;

public interface VifoCreateRevaOrderInterface {

    public Map<String, Object> createRevaOrder(Map<String, String> headers, Map<String, Object> body);
}
