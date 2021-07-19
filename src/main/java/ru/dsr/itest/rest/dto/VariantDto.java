package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.db.entity.VariantConfig;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static VariantDto from(Variant variant) {
        VariantDto dto = new VariantDto();
        dto.id = variant.getId();
        dto.testId = variant.getTest().getId();
        dto.number = variant.getNumber();
        dto.questions = variant.getConfigs()
                .stream()
                .collect(Collectors.toMap(VariantConfig::getNumber, c -> c.getId().getQuestion().getId()));

        return dto;
    }
}
