package org.company.cache.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.company.cache.external.jms.to.ProductInfo;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class HazelcastClient {
    private final HazelcastInstance instance;

    @Getter
    private IMap<Integer, ProductInfo> jmsMessage;

    @PostConstruct
    public void init() {
        jmsMessage = instance.getMap("jmsMessage");
    }
}
