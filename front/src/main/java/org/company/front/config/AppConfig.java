package org.company.front.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class AppConfig {

    @Value("${DB_ADDRESS:http://localhost:8081}")
    private String dbUri;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        log.info("Database URI - {}", dbUri);
        return restTemplateBuilder
                .rootUri(dbUri)
                //.setConnectTimeout(Duration.ofSeconds(2))
                //.setReadTimeout(Duration.ofSeconds(2))
                .build();
    }
}
