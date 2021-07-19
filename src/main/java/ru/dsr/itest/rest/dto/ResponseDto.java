package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Response;

import java.util.List;

@Getter
public class ResponseDto {
    private Integer id;
    private Integer test;
    private ExamVariantDto variant;
    private List<Integer> choiceIdList;

    public static ResponseDto from(Response response) {
        ResponseDto dto = new ResponseDto();
        dto.id = response.getId();
        dto.test = response.getTest().getId();
        dto.choiceIdList = response.getAnswers()
                .stream()
                .map(a -> a.getId().getChoiceId())
                .toList();
        dto.variant = ExamVariantDto.from(response.getVariant());
        return dto;
    }
}
