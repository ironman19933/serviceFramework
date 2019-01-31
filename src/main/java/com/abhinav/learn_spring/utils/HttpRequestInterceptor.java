package com.abhinav.learn_spring.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class HttpRequestInterceptor implements org.springframework.http.client.ClientHttpRequestInterceptor {
    @Value("${axle.client.id}")
    private String clientId;
    @Value("${axle.client.secret}")
    private String clientSecret;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("X-Client-Id", clientId);
        headers.add("X-Client-Id", clientSecret);
        headers.add("X-Requested-By", "1");
        return execution.execute(request, body);
    }
}
