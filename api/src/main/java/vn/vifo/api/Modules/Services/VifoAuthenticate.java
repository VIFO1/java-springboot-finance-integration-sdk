package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.vifo.api.Interfaces.VifoAutheticateInterface;

public class VifoAuthenticate implements VifoAutheticateInterface {
    private VifoSendRequest sendRequest;

    public VifoAuthenticate(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
    }

    public List<String> validateLoginInput(Map<String, String> headers, String username, String password) {
        List<String> errors = new ArrayList<>();
        if (username == null || username.trim().isEmpty()) {
            errors.add(" Invalid username");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.add("Invalid password");
        }

        if (headers == null || headers.isEmpty()) {
            errors.add("Headers must not be null or empty");
        }
        return errors;
    }

    public Map<String, Object> buildLoginBody(String username, String password) {
        return Map.of(
                "username", username,
                "password", password);
    }

    public Map<String, Object> authenticateUser(Map<String, String> headers, String username, String password) {
        String endpoint = "/v1/clients/web/admin/login";
        List<String> errors = validateLoginInput(headers, username, password);
        if (!errors.isEmpty()) {
            return Map.of("errors", errors);
        }
        Map<String, Object> body = buildLoginBody(username, password);
        return this.sendRequest.sendRequest("POST", endpoint, headers, body);
    }
}
