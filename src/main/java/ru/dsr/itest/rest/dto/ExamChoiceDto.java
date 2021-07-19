package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Choice;

@Getter
public class ExamChoiceDto {
    private Integer id;
    private String title;

    public static ExamChoiceDto from(Choice c) {
        ExamChoiceDto dto = new ExamChoiceDto();
        dto.id = c.getId();
        dto.title = c.getTitle();
        return dto;
    }
}
