package io.riguron.stackexchange.polling;

import io.riguron.stackexchange.api.StackExchangeClient;
import io.riguron.stackexchange.api.query.Question;
import io.riguron.stackexchange.api.query.QuestionsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

@Slf4j
public class UpdatesPoller {

    private StackExchangeClient exchange;
    private NewPostHandler newPostHandler;
    private Options options;
    private Map<String, Long> lastPollTimes = new HashMap<>();

    public UpdatesPoller(StackExchangeClient exchange, Options options, NewPostHandler newPostHandler) {
        this.exchange = exchange;
        this.newPostHandler = newPostHandler;
        this.options = options;
    }

    @Scheduled(fixedDelayString = "${polling.delay}")
    public void poll() {
        options.getSites().forEach(site -> {
            lastPollTimes.compute(site, (s, lastPollTime) -> {
                if (lastPollTime != null) {
                    List<Question> questions = exchange.get(query(site, lastPollTime)).getItems();
                    for (Question question : questions) {
                        if (Collections.disjoint(options.getIgnored(), question.getTags())) {
                            newPostHandler.newPost(question);
                            log.info("New post created: {}", question);
                        }
                    }
                }
                return System.currentTimeMillis();
            });

        });
    }


    private QuestionsQuery query(String site, long time) {
        return new QuestionsQuery()
                .order("desc")
                .fromDate(time / 1000)
                .sort("creation")
                .tagged(options.getTags())
                .site(site);
    }


}
