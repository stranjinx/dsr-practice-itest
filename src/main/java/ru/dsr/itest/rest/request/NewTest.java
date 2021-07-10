package ru.dsr.itest.rest.request;

import lombok.Getter;
import ru.dsr.itest.db.entity.Discipline;

import javax.validation.constraints.NotNull;

@Getter
public class NewTest {
    @NotNull
    private Discipline discipline;
}
