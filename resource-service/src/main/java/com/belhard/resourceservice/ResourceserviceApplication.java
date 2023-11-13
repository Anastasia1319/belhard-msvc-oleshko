package com.belhard.resourceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ResourceserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceserviceApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
