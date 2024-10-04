package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.Services.VifoServiceFactory;

public class VifoServiceFactoryTest {
    public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoServiceFactory serviceFactory = new VifoServiceFactory(sendRequest);

        Map<String, Object> test = serviceFactory.fetchBeneficiaryName(null);

        System.out.println(test);
    }
}
