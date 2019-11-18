package io.riguron.stackexchange.polling;

import io.riguron.stackexchange.api.query.Question;

public interface NewPostHandler {

    void newPost(Question question);
}
