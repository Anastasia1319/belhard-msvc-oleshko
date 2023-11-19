package com.belhard.resourceservice;

import org.springframework.beans.factory.annotation.Value;
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
    public WebClient webClient(@Value("${clients.song-service}") final String hostName,
                               @Value("${clients.contextPath}") final String contextPath) {
        return WebClient
                .builder()
                .baseUrl(hostName + contextPath)
                .build();
    }
}
