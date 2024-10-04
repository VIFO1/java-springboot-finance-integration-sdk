package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoOtherRequest;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoOtherRequestTest {
    public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoOtherRequest otherRequest = new VifoOtherRequest(sendRequest);
        Map<String, Object> test = otherRequest.checkOrderStatus(null, null);
        System.out.println(test);
    }
}
