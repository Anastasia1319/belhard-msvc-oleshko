package com.belhard.resourceservice.client.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class SongClientImpl implements SongClient {

    private final WebClient webClient;

    @Override
    public void create(MetaDataDto metaDataDto) {
        String response = webClient.post()
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
                .uri("?id=" + resourceIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response to song-service: {}", response);
    }
}
