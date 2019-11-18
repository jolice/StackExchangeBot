package io.riguron.stackexchange.api;

import io.riguron.stackexchange.api.query.Query;
import io.riguron.stackexchange.api.query.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Supplier;

@Component
@Slf4j
public class StackExchangeClient {

    private static final String ENDPOINT = "https://api.stackexchange.com/2.2/%s?";

    private final RestTemplate restTemplate;

    public StackExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <R extends Response> R get(Query<?, R> query) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(buildUrl(query));
        query.build().forEach(uriComponentsBuilder::queryParam);
        return doExchange(() -> restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, RequestEntity.EMPTY, query.responseType()));
    }

    private <R extends Response> R doExchange(Supplier<ResponseEntity<? extends R>> response) {
        ResponseEntity<? extends R> result = response.get();
        log.info("Quota remaining: " + result.getBody().getQuotaRemaining());
        return result.getBody();
    }

    private String buildUrl(Query<?, ?> query) {
        return String.format(ENDPOINT, query.method());
    }
}
