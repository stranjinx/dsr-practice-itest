package ru.dsr.itest.rest.response;

import lombok.Getter;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.rest.dto.QuestionDto;

import java.util.List;

@Getter
public class VariantView {
    private Integer number;
    private Integer testId;
    private List<QuestionView> questions;
}
