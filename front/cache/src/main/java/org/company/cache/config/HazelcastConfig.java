package org.company.cache.config;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hazelcast.config.*;

@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
        return Hazelcast.newHazelcastInstance(hazelCastConfig);
    }

    @Bean
    public Config hazelCastConfig() {
        return new Config()
            .setInstanceName("hazelcast-instance")
            .addMapConfig(jmsMessageMap());
    }

    public MapConfig jmsMessageMap() {
        return new MapConfig()
                .setName("jmsMessage");
                //.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                //.setEvictionPolicy(EvictionPolicy.LRU)
                //.setTimeToLiveSeconds(-1);
    }
}
