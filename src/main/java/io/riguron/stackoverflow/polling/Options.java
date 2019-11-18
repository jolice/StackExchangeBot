package io.riguron.stackoverflow.polling;

import lombok.Value;

import java.util.List;

@Value
public class Options {

    private final List<String> sites;
    private final List<String> tags;
    private List<String> ignored;

}
