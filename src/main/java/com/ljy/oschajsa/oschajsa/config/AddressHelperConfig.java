package com.ljy.oschajsa.oschajsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AddressHelperConfig {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
