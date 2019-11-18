package io.riguron.stackexchange.polling;

import io.riguron.stackexchange.api.StackExchangeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class PollingConfiguration {


    @Bean
    public UpdatesPoller updatesPoller(StackExchangeClient stackExchangeClient, Options options, NewPostHandler newPostHandler) {
        return new UpdatesPoller(stackExchangeClient, options, newPostHandler);
    }

    @Bean
    public Options options(@Value("${sites}") String[] sites, @Value("${tags}") String[] tags, @Value("${ignore}") String[] ignore) {
        return new Options(Arrays.asList(sites), Arrays.asList(tags), Arrays.asList(ignore));
    }
}
