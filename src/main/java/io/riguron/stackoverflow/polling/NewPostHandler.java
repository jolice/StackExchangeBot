package io.riguron.stackoverflow.polling;

import io.riguron.stackoverflow.api.query.Question;

public interface NewPostHandler {

    void newPost(Question question);
}
