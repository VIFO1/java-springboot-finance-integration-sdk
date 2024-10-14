package vn.vifo.api;


import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse;
import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.Services.VifoServiceFactory;

import java.util.Map;
public class VifoServiceFactoryTest {
    public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoServiceFactory serviceFactory = new VifoServiceFactory(sendRequest);
        Map<String,Object> body = Map.of("1","1","2","2");
        BeneficiaryNameResponse test = serviceFactory.fetchBeneficiaryName(body);

        System.out.println(test);
    }
}
