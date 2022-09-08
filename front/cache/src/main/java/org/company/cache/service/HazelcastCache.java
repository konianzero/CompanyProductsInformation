package org.company.cache.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.cache.hazelcast.HazelcastClient;
import org.company.cache.external.jms.JmsClient;
import org.company.cache.external.jms.to.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class HazelcastCache {
    private final HazelcastClient hazelcastClient;
    private final JmsClient jmsClient;

    @PostConstruct
    public void init() {
        log.info("{} started", this.getClass().getSimpleName());
        log.info("HazelcastCache.HazelcastClient autowired: {}", Objects.nonNull(hazelcastClient));
        log.info("HazelcastCache.JmsClient autowired: {}", Objects.nonNull(jmsClient));
    }

    public List<ProductInfo> getData(final ProductsInfoRequest payload) {
        log.info("Request - {}", payload);
        List<ProductInfo> productsInfo = payload.getIds().stream().map(i -> hazelcastClient.getJmsMessage().get(i)).toList();

        if (!productsInfo.isEmpty() && productsInfo.get(0) != null) {
            log.info("Get data from cache");
        } else {
            log.info("Get data from external");
            productsInfo = jmsClient.requestData(payload);
            // TODO - https://reflectoring.io/spring-boot-hazelcast/#serialization
            productsInfo.forEach(p -> hazelcastClient.getJmsMessage().put(payload.getIds().get(0), p));
        }
        return productsInfo;
    }
}
