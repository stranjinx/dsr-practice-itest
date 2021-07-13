package ru.dsr.itest.rest.dto;

import lombok.Getter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class VariantDto {
    @NotNull
    private Integer id;
    @NotNull
    private Integer testId;
    @NotNull
    Integer number;
    @NotEmpty
    private Map<Integer, Integer> questions;

    private List<Integer> deletedQuestions = new ArrayList<>();
}
