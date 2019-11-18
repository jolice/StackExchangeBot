package io.riguron.stackoverflow.api.query;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class QuestionsQuery extends Query<QuestionsQuery, Questions> {

    public QuestionsQuery() {
        super("questions");
    }

    @Override
    QuestionsQuery self() {
        return this;
    }

    @Override
    public ParameterizedTypeReference<Questions> responseType() {
        return new ParameterizedTypeReference<Questions>() {
        };
    }

    public QuestionsQuery fromDate(long fromDate) {
        return param("fromDate", fromDate);
    }

    public QuestionsQuery order(String order) {
        return param("order", order);
    }

    public QuestionsQuery sort(String sort) {
        return param("sort", sort);
    }

    public QuestionsQuery tagged(List<String> tagged) {
        return param("tagged", String.join(";", tagged));
    }

    public QuestionsQuery site(String site) {
        return param("site", site);
    }
}
