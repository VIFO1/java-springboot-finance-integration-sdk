package vn.vifo.api;


import vn.vifo.api.Modules.Converters.OtherRequestResponse;
import vn.vifo.api.Modules.Services.VifoOtherRequest;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoOtherRequestTest {
    public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoOtherRequest otherRequest = new VifoOtherRequest(sendRequest);
        OtherRequestResponse test = otherRequest.checkOrderStatus(null, null);
        System.out.println(test);
    }
}
