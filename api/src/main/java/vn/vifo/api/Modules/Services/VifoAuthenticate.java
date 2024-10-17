package vn.vifo.api.Modules.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vifo.api.Interfaces.VifoAutheticateInterface;
import vn.vifo.api.Modules.DTO.AuthenticateResponse;
import vn.vifo.api.Ultils.HttpStatusUtils;
import vn.vifo.api.Ultils.JsonParserUtils;

public class VifoAuthenticate implements VifoAutheticateInterface {
    private VifoSendRequest sendRequest;
    private ObjectMapper objectMapper;

    public VifoAuthenticate(VifoSendRequest sendRequest) {
        this.sendRequest = sendRequest;
        this.objectMapper = new ObjectMapper();
    }

    public List<String> validateLoginInput(HashMap<String, String> headers, String username, String password) {
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

    public HashMap<String, Object> buildLoginBody(String username, String password) {
        HashMap<String, Object> loginBody = new HashMap<>();
        loginBody.put("username", username);
        loginBody.put("password", password);
        return loginBody;
    }

    public AuthenticateResponse authenticateUser(HashMap<String, String> headers, String username, String password) {
        String endpoint = "/v1/clients/web/admin/login";
        List<String> errors = validateLoginInput(headers, username, password);
        if (!errors.isEmpty()) {
            return AuthenticateResponse.builder()
                    .body(AuthenticateResponse.Body.builder()
                            .errors(String.join(",", errors))
                            .build())
                    .build();
        }

        HashMap<String, Object> body = buildLoginBody(username, password);
        try {
            HashMap<String, Object> apiResponse = this.sendRequest.sendRequest("POST", endpoint, headers, body);

            HttpStatus statusCode = (HttpStatus) apiResponse.get("status_code");
            if (statusCode == null || !statusCode.equals(HttpStatus.OK)) {
                String errorMessage = (String) apiResponse.get("errors");
                return AuthenticateResponse.builder()
                        .statusCode(HttpStatusUtils.getStatusMessage(statusCode))
                        .body(AuthenticateResponse.Body.builder()
                                .message("Error: " + errorMessage)
                                .build())
                        .build();
            }
            String jsonResponse = JsonParserUtils.stringify(apiResponse);
            AuthenticateResponse authenticateResponse = objectMapper.readValue(jsonResponse,
                    AuthenticateResponse.class);
            return authenticateResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
