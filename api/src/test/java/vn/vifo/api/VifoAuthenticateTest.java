package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoAuthenticate;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoAuthenticateTest {
     public static void main(String[] args)
    {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoAuthenticate authenticate = new VifoAuthenticate(sendRequest);
        String username = "";
        String passsword = "";
    
        Map<String, String> headers = Map.of("","");
        Map<String, Object> test = authenticate.authenticateUser(headers,username,passsword);
        System.out.println(test);
    }
}
