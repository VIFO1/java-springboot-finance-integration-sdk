package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoAutheticateInterface;
import vn.vifo.api.Modules.Converters.AuthenticateResponse;

public class VifoAuthenticate implements VifoAutheticateInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoAuthenticate(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();
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

    public AuthenticateResponse authenticateUser(Map<String, String> headers, String username, String password) {
        String endpoint = "/v1/clients/web/admin/login";
        List<String> errors = validateLoginInput(headers, username, password);
        if (!errors.isEmpty()) {
            return AuthenticateResponse.builder()
                    .body(AuthenticateResponse.Body.builder()
                            .errors(String.join(",", errors))
                            .build())
                    .build();
        }
        Map<String, Object> body = buildLoginBody(username, password);
        try {
            Map<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);

            HttpStatus httpStatusCode = (HttpStatus) apiResponse.get("status_code");

            if (httpStatusCode == null || !httpStatusCode.equals(HttpStatus.OK)) {
                String errorMessage = (String) apiResponse.get("errors");

                return AuthenticateResponse.builder()
                        .statusCode(httpStatusCode)
                        .body(AuthenticateResponse.Body.builder()
                                .message("errorMessage" + errorMessage)
                                .build())
                        .build();
            }
            String jsonResponse = objectMapper.writeValueAsString(apiResponse);
            AuthenticateResponse authenticateResponse = objectMapper.readValue(jsonResponse,
                    AuthenticateResponse.class);
            return authenticateResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
