package org.company.front.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateService {

    protected Map<String, Integer> uriVariable(int id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        return params;
    }

    protected HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
