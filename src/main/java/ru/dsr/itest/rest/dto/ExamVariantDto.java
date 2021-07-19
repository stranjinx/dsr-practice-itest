package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.db.entity.VariantConfig;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ExamVariantDto {
    private Integer id;
    private Integer number;
    private Map<Integer, ExamQuestionDto> questions;

    public static ExamVariantDto from(Variant v) {
        ExamVariantDto dto = new ExamVariantDto();
        dto.id = v.getId();
        dto.number = v.getNumber();
        dto.questions = v.getConfigs()
                .stream()
                .collect(Collectors.toMap(
                        VariantConfig::getNumber,
                        c -> ExamQuestionDto.from(c.getId().getQuestion()))
                );

        return dto;
    }
}
