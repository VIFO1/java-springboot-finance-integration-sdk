package vn.vifo.api;

import java.util.Map;

import vn.vifo.api.Modules.Services.VifoBank;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoBankTest {
    public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoBank bank = new VifoBank(sendRequest);

        Map<String, Object> test = bank.getBank(null);
        System.out.println(test);
    }
}
