package org.company.remote.config;

import org.company.remote.to.ProductInfo;
import org.company.remote.to.ProductIntegration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MockConfig {

    @Bean
    public Map<Integer, ProductInfo> mockBase() {
        Map<Integer, ProductInfo> infoMap = new HashMap<>();
        infoMap.put(
                100000,
                new ProductInfo(
                        "Продукт номер один",
                        List.of(
                                new ProductIntegration("ТрансМета", LocalDate.of(2021, 2, 10)),
                                new ProductIntegration("КомЭнерго", LocalDate.of(2022, 4, 14))
                        )
                )
        );
        infoMap.put(
                100001,
                new ProductInfo("Продукт номер два",
                        List.of(
                                new ProductIntegration("ТехИнж", LocalDate.of(2022, 3, 18))
                        )
                )
        );
        return infoMap;
    }
}
