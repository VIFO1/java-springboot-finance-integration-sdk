package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.vifo.api.Interfaces.VifoBankInterface;

public class VifoBank implements VifoBankInterface {
    private VifoSendRequest sendRequest;

    public VifoBank(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
    }

    public List<String> validateBody(Map<String, String> headers, Map<String, Object> body) 
    {
        List<String> errors = new ArrayList<>();
        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        }
        return errors;
    }

    public Map<String, Object> getBank(Map<String, String> headers) {
        String endpoint = "/v2/data/banks/napas";
        if (headers == null || headers.isEmpty()) {
            return Map.of("headers", "must not be empty");
        }
        return sendRequest.sendRequest("GET", endpoint, headers, null);
    }

    public Map<String, Object> getBeneficiaryName(Map<String, String> headers,Map<String, Object> body)
    {
        String endpoint = "/v2/finance/napas/receiver";
        List<String> errors = validateBody(headers, body);
        if(!errors.isEmpty())
        {
            return Map.of("errors", errors);
        }
        return this.sendRequest.sendRequest("POST", endpoint, headers, body);
    }
}
