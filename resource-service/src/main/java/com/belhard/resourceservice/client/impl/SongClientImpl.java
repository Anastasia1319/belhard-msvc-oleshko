package com.belhard.resourceservice.client.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class SongClientImpl implements SongClient {

    private final WebClient webClient;
    private final EurekaClient eurekaClient;
    private final LoadBalancerClient loadBalancerClient;

    @Value("${clients.song-service}")
    private String songServiceId;

    @Override
    public void create(MetaDataDto metaDataDto) {
        String response = webClient.post()
                .uri(getUri())
                .bodyValue(metaDataDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response to song-service: {}", response);
    }

    @Override
    public void deleteByResourceId(List<Long> resourceIds) {
        String response = webClient.delete()
                .uri(getUri() + "?id=" + resourceIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response to song-service: {}", response);
    }

    private URI getUri() {
        ServiceInstance choose = loadBalancerClient.choose(songServiceId);
        log.info("CHOSEN: getUri {}", choose.getUri());
        return choose.getUri();
    }
}
