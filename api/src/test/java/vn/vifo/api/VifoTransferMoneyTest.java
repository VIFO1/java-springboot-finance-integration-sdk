package vn.vifo.api;

import java.util.List;

import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.Services.VifoTransferMoney;

public class VifoTransferMoneyTest {
        public static void main(String[] args) {
        VifoSendRequest sendRequest = new VifoSendRequest("dev");
        VifoTransferMoney transferMoney = new VifoTransferMoney(sendRequest);

        List<String> test = transferMoney.validateRequestInput(null,null);

        System.out.println(test);
    }
}
