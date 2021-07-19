package ru.dsr.itest.rest.response;

import ru.dsr.itest.db.entity.Discipline;

import java.sql.Timestamp;

public interface TestExamInfoView {
    Integer getId();
    String getTitle();
    Discipline getDiscipline();
    Timestamp getTimeStart();
    Timestamp getTimeEnd();
}
