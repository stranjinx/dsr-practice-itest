package ru.dsr.itest.rest.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class AnswersDto {
    @NotNull
    private List<Integer> choiceIdList;
}
