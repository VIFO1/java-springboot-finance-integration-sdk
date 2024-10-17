package vn.vifo.api.Interfaces;

import java.util.HashMap;
import java.util.List;

import vn.vifo.api.Modules.DTO.AuthenticateResponse;

public interface VifoAutheticateInterface {
    public List<String> validateLoginInput(HashMap<String, String> headers, String username, String password);

    public HashMap<String, Object> buildLoginBody(String username, String password);

    public AuthenticateResponse authenticateUser(HashMap<String, String> headers, String username, String password);
}
