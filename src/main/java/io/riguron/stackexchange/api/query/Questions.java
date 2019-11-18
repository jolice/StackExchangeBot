package io.riguron.stackexchange.api.query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = true)
@Getter
public class Questions extends Response {

    private List<Question> items;

}
