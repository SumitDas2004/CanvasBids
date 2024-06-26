package com.canvasbids.gateway.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Configurations {
    @Bean
    RestClient restClient(){
        return RestClient.create();
    }
}
