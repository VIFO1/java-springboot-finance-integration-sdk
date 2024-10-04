package vn.vifo.api.CommonFunctions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.google.gson.Gson;

public class CommonFunctions {
    public List<String> validateSignatureInputs(String secretKey, String timestamp, Map<String, Object> body) {
        List<String> errors = new ArrayList<>();
        if (secretKey == null || secretKey.isEmpty()) {
            errors.add("secretKey must not be empty");
        }
        if (timestamp == null || timestamp.isEmpty()) {
            errors.add("timestamp must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        }

        return errors;
    }

    public String generateSignature(Map<String, Object> body, String secretKey, String timestamp) {
        try {
            Gson gson = new Gson();
            Map<String, Object> sortedBody = new TreeMap<>(body);
            String jsonBody = gson.toJson(sortedBody);
            String signatureString = timestamp + jsonBody;
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);

            return Hex.encodeHexString(sha256_HMAC.doFinal(signatureString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return "Signature creation failed: " + e.getMessage();
        }
    }

    public List<String> validateCreateOrder(Map<String, String> headers, Map<String, Object> body) {
        List<String> errors = new ArrayList<>();

        if (headers == null || headers.isEmpty()) {
            errors.add("headers must not be empty");
        }
        if (body == null || body.isEmpty()) {
            errors.add("body must not be empty");
        } else {
            String[] requiredFields = {
                    "product_code",
                    "phone",
                    "fullname",
                    "final_amount",
                    "distributor_order_number",
                    "benefiary_account_no",
                    "benefiary_bank_code",
                    "comment",
            };

            for (String field : requiredFields) {
                if (!body.containsKey(field)) {
                    errors.add(field + "is required.");
                }
            }
        }

        return errors;
    }
}
