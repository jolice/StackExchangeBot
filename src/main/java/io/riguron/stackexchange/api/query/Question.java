package io.riguron.stackexchange.api.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @JsonProperty("question_id")
    private Integer id;
    private String title;
    private String link;
    private List<String> tags;

}
