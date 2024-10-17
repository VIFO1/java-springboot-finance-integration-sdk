package vn.vifo.api;

import java.util.HashMap;

import vn.vifo.api.Modules.DTO.AuthenticateResponse;
import vn.vifo.api.Modules.Services.VifoAuthenticate;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoAuthenticateTest {
     public static void main(String[] args)
    {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoAuthenticate authenticate = new VifoAuthenticate(sendRequest);
        String username = "";
        String passsword = "";
    
        HashMap<String, String> headers =null;
        AuthenticateResponse test = authenticate.authenticateUser(headers,username,passsword);
        System.out.println(test);
    }
}
