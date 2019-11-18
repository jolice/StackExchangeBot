package io.riguron.stackexchange.api.query;

import org.springframework.core.ParameterizedTypeReference;

import java.util.HashMap;
import java.util.Map;

public abstract class Query<Q, R extends Response> {

    private String method;
    private Map<String, String> parameters = new HashMap<>();

    Query(String method) {
        this.method = method;
    }

    Q param(String key, String value) {
        parameters.put(key, value);
        return self();
    }

    Q param(String key, Long value) {
        return param(key, String.valueOf(value));
    }

    public String method() {
        return method;
    }

    abstract Q self();

    public abstract ParameterizedTypeReference<? extends R> responseType();

    public Map<String, String> build() {
        return parameters;
    }
}
