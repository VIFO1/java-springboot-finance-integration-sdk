package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.Services.VifoCreateRevaOrder;

public class VifoCreateRevaOrderTest {
     public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoCreateRevaOrder createRevaOrderTest = new VifoCreateRevaOrder(sendRequest);

        Map<String, Object> test = createRevaOrderTest.createRevaOrder(null, null);
        System.out.println(test);
    }
}
