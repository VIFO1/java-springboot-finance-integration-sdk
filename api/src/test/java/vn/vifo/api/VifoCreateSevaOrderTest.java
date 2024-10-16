package vn.vifo.api;


import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.DTO.CreateSevaOrderResponse;
import vn.vifo.api.Modules.Services.VifoCreateSevaOrder;

public class VifoCreateSevaOrderTest {
     public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoCreateSevaOrder createSevaOrderTest = new VifoCreateSevaOrder(sendRequest);

        CreateSevaOrderResponse test = createSevaOrderTest.createSevaOrder(null, null);
        System.out.println(test);
    }
}
