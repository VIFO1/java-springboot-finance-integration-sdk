package vn.vifo.api.Modules;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import vn.vifo.api.Modules.Services.VifoSendRequest;
import vn.vifo.api.Modules.Services.VifoServiceFactory;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@ComponentScan("vn.vifo.api.Modules")
@PropertySource("classpath:application.properties")

public class AppConfig {

    @Bean
    @Profile("dev")
    public VifoSendRequest vifoSendRequestDev(@Value("${vifo_api_url_dev}") String apiUrl) {
        return new VifoSendRequest(apiUrl);
    }
    
    @Bean
    @Profile("stg")
    public VifoSendRequest vifoSendRequestStg(@Value("${vifo_api_url_stg}") String apiUrl) {
        return new VifoSendRequest(apiUrl);
    }
    
    @Bean
    @Profile("production")
    public VifoSendRequest vifoSendRequestProduction(@Value("${vifo_api_url_prod}") String apiUrl) {
        return new VifoSendRequest(apiUrl);
    }
    
    @Bean
    public VifoServiceFactory vifoServiceFactory(VifoSendRequest sendRequest) {
        return new VifoServiceFactory(sendRequest);
    }
}
