package ru.dsr.itest.rest.response;

import lombok.Getter;
import ru.dsr.itest.db.entity.Discipline;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.db.entity.Variant;

import java.sql.Timestamp;

@Getter
public class TestFullView {
    private Integer id;
    private Integer creator;
    private String title;
    private Discipline discipline;
    private Timestamp timeStart;
    private Timestamp timeEnd;

    public static TestFullView from(Test test) {
        TestFullView view = new TestFullView();
        view.id = test.getId();
        view.creator = test.getCreator();
        view.title = test.getTitle();
        view.discipline = test.getDiscipline();
        view.timeStart = test.getTimeStart();
        view.timeEnd = test.getTimeEnd();
        return view;
    }
}
