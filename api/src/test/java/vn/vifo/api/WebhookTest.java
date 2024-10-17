package vn.vifo.api;

import vn.vifo.api.Modules.Services.Webhook;

public class WebhookTest {
    public static void main(String[] args) {
        Webhook webhook = new Webhook();

        boolean test = webhook.handleSignature(null, null, null, null);
        System.out.println(test);
    }
}
