package com.livspace.service_framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Autowired
    private Environment env;

    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(getClientHttpRequestFactory())
                .interceptors(getHttpRequestInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor getHttpRequestInterceptor() {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("X-Client-Id", env.getProperty("axle.client.id"));
            headers.add("X-Client-Id", env.getProperty("axle.client.secret"));
            headers.add("X-Requested-By", "1");
            return execution.execute(request, body);
        };
    }
}
