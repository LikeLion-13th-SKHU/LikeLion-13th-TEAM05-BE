package com.likelion.artipick.domain.culture.infra;

import com.likelion.artipick.domain.culture.dto.api.Period2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CultureOpenApiClient {

    private final WebClient cultureWebClient;

    @Value("${openapi.culture.service-key}")
    private String serviceKey;

    // 이름과 파라미터 맞춤
    public Period2Response fetchPeriod(LocalDate from, LocalDate to, int page, int size) {
        return cultureWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/period2")
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("from", from.toString())
                        .queryParam("to", to.toString())
                        .queryParam("pageNo", page)
                        .queryParam("numOfRows", size)
                        .build())
                .retrieve()
                .bodyToMono(Period2Response.class)
                .block();
    }
}
