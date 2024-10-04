package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.Map;

import vn.vifo.api.CommonFunctions.CommonFunctions;
import vn.vifo.api.Interfaces.VifoCreateSevaOrderInterface;

public class VifoCreateSevaOrder implements VifoCreateSevaOrderInterface{
    private VifoSendRequest sendRequest;
    private CommonFunctions commonFunctions;

    public VifoCreateSevaOrder(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.commonFunctions = new CommonFunctions();
    }

    public Map<String, Object> createSevaOrder(Map<String, String> headers, Map<String, Object> body) {
        String endpoint = "/v2/finance";
        List<String> errors = this.commonFunctions.validateCreateOrder(headers, body);
        if (!errors.isEmpty()) {
            return Map.of("errors", errors);
        }
        return this.sendRequest.sendRequest("POST", endpoint, headers, body);
    }
}
