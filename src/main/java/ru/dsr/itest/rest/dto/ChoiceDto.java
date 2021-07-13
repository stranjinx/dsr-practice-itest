package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Choice;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class ChoiceDto {
    private Integer id;
    @NotBlank
    @Size(max = 256)
    private String title;
    private boolean correct;

    public static ChoiceDto from(Choice c) {
        ChoiceDto s = new ChoiceDto();
        s.correct = c.getCorrect();
        s.title = c.getTitle();
        s.id = c.getId();
        return s;
    }
}
