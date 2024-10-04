package vn.vifo.api.Modules.Services;

import java.util.List;
import java.util.Map;

import vn.vifo.api.CommonFunctions.CommonFunctions;

public class Webhook {
    private CommonFunctions commonFunctions;

    public Webhook() {
        this.commonFunctions = new CommonFunctions();
    }

    public String createSignature(Map<String, Object> body, String secretKey, String timestamp) {

        return this.commonFunctions.generateSignature(body, secretKey, timestamp);
    }

    public boolean handleSignature(Map<String, Object> data, String requestSignature, String secretKey,
            String timestamp) {
        List<String> errors = this.commonFunctions.validateSignatureInputs(secretKey, timestamp, data);

        if (!errors.isEmpty()) {
            return false;
        }

        String generatedSignature = createSignature(data, secretKey, timestamp);
        if (generatedSignature.equals(requestSignature)) {
            return true;
        } else {
            return false;
        }
    }
}
