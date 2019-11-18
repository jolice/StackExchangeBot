package io.riguron.stackexchange.api;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration

@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate(HttpClient httpClient) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        return restTemplate;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(3 * 1000).build())
                .setConnectionTimeToLive(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        return HttpClients.createDefault();
    }



}
