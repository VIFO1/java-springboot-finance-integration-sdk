package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.Services.VifoCreateSevaOrder;

public class VifoCreateSevaOrderTest {
     public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoCreateSevaOrder createSevaOrderTest = new VifoCreateSevaOrder(sendRequest);

        Map<String, Object> test = createSevaOrderTest.createSevaOrder(null, null);
        System.out.println(test);
    }
}
