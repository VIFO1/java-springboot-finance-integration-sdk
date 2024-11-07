package vn.vifo.api;

import vn.vifo.api.Modules.AppConfig;
import vn.vifo.api.Modules.DTO.AuthenticateResponse;

import vn.vifo.api.Modules.Services.VifoServiceFactory;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class VifoServiceFactoryTest {
    public static void main(String[] args) {
        // VifoSendRequest sendRequest = new VifoSendRequest("dev");
        // VifoServiceFactory serviceFactory = new VifoServiceFactory(sendRequest);
        // Map<String,Object> body = Map.of("1","1","2","2");
        // BeneficiaryNameResponse test = serviceFactory.fetchBeneficiaryName(body);
        System.setProperty("spring.profiles.active", "dev");

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        VifoServiceFactory vifoServiceFactory = context.getBean(VifoServiceFactory.class);
        AuthenticateResponse test = vifoServiceFactory.performUserAuthentication("", "");
        System.out.println(test);
    }
}
