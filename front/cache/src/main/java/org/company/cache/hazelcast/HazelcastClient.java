package org.company.cache.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.company.cache.external.jms.to.ProductInfo;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class HazelcastClient {
    private final HazelcastInstance instance;

    @Getter
    private IMap<Integer, ProductInfo> jmsMessage;

    @PostConstruct
    public void init() {
        log.info("{} started", this.getClass().getSimpleName());
        jmsMessage = instance.getMap("jmsMessage");
    }
}
