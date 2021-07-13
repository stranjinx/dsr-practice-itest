package ru.dsr.itest.rest.response;

import lombok.Getter;
import ru.dsr.itest.db.entity.Variant;

import java.util.List;

@Getter
public class TestVariantsView {
    private Integer id;
    private Integer number;

    public static List<TestVariantsView> from(List<Variant> list) {
        return list
                .stream()
                .map(TestVariantsView::from)
                .toList();
    }
    public static TestVariantsView from(Variant variant) {
        TestVariantsView dto = new TestVariantsView();
        dto.id = variant.getId();
        dto.number = variant.getNumber();
        return dto;
    }
}
