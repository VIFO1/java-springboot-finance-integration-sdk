package vn.vifo.api;

import java.util.HashMap;

import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoSendRequestTest {
     public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        
        HashMap<String, Object> test = sendRequest.sendRequest(null,null,null, null);
        System.out.println(test);
    }
}
