package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Discipline;
import ru.dsr.itest.db.entity.Test;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class TestCreateDto {
    @NotNull
    private Discipline discipline;
    @NotBlank
    @Size(max = 256)
    private String title;

    public Test toTest() {
        Test test = new Test();
        test.setTitle(getTitle());
        test.setDiscipline(discipline);
        return test;
    }
}
