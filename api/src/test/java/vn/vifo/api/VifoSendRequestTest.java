package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoSendRequestTest {
     public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        
        Map<String, Object> test = sendRequest.sendRequest(null,null,null, null);
        System.out.println(test);
    }
}
