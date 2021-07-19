package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Choice;
import ru.dsr.itest.db.entity.Question;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ExamQuestionDto {
    private Integer id;
    private String title;
    private String type;
    private Map<Integer, ExamChoiceDto> choices;

    public static ExamQuestionDto from(Question q) {
        ExamQuestionDto dto = new ExamQuestionDto();

        dto.id = q.getId();
        dto.title = q.getTitle();
        dto.type = q.getChoices().stream().filter(Choice::getCorrect).count() > 1 ? "MANY" : "ONE";
        dto.choices = q.getChoices()
                .stream()
                .collect(Collectors.toMap(Choice::getNumber, ExamChoiceDto::from));
        return dto;
    }
}
