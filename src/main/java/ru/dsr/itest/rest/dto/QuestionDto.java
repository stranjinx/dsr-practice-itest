package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Choice;
import ru.dsr.itest.db.entity.Question;
import ru.dsr.itest.rest.response.QuestionView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class QuestionDto {
    @NotNull
    private Integer id;
    @NotNull
    private Integer testId;
    @NotBlank
    @Size(max = 256)
    private String title;
    @NotNull
    private Integer weight;
    @Size(min = 2, max = 10)
    private Map<Integer, ChoiceDto> choices;

    public static QuestionDto from(Question quest, boolean choices) {
        QuestionDto s = new QuestionDto();

        s.id = quest.getId();
        s.testId = quest.getTestId();
        s.title = quest.getTitle();
        s.weight = quest.getWeight();
        if (choices)
            s.choices = quest.getChoices()
                    .stream()
                    .collect(Collectors.toMap(Choice::getNumber, ChoiceDto::from));

        return s;
    }

}
