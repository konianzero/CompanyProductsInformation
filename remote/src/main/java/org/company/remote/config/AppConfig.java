package org.company.remote.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    public void registerObjectMapperModules(ObjectMapper mapper) {
        mapper.findAndRegisterModules();
    }
}
