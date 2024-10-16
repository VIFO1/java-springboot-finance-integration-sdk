package vn.vifo.api;


import vn.vifo.api.Modules.DTO.BankResponse;
import vn.vifo.api.Modules.Services.VifoBank;
import vn.vifo.api.Modules.Services.VifoSendRequest;

public class VifoBankTest {
    public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoBank bank = new VifoBank(sendRequest);

        BankResponse test = bank.getBank(null);
        System.out.println(test);
    }
}
