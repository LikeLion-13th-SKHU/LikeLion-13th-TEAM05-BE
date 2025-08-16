package com.likelion.artipick.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenApiConfig {

    @Value("${openapi.culture.base-url}")
    private String baseUrl;

    @Bean
    public WebClient cultureWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
