package ru.dsr.itest.rest.response;

import ru.dsr.itest.db.entity.Discipline;

public interface TestView {
    Integer getId();
    String getTitle();
    Discipline getDiscipline();
}
