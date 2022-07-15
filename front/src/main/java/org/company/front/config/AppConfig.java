package org.company.front.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.company.front.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Autowired
    public void storeObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                //.rootUri("http://localhost:8081/articles/")
                //.setConnectTimeout(Duration.ofSeconds(2))
                //.setReadTimeout(Duration.ofSeconds(2))
                .build();
    }
}
