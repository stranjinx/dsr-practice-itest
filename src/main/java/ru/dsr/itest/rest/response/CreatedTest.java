package ru.dsr.itest.rest.response;

import ru.dsr.itest.db.entity.Discipline;

public interface CreatedTest {
    Integer getId();
    String getTitle();
    Discipline getDiscipline();
}
