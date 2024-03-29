package com.champlain.ateliermecaniquews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AteliermecaniqueWsApplication {

    @Bean
    RestTemplate restTemplate() {return new RestTemplate();}

    public static void main(String[] args) {
        SpringApplication.run(AteliermecaniqueWsApplication.class, args);
    }

}
