package org.company.front.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri("http://localhost:8081")
                //.setConnectTimeout(Duration.ofSeconds(2))
                //.setReadTimeout(Duration.ofSeconds(2))
                .build();
    }
}
