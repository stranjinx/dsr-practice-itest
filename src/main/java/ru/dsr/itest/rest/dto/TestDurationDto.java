package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.rest.validation.Duration;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Duration(target = Duration.Target.FUTURE)
public class TestDurationDto {
    @NotNull
    private Timestamp timeStart;
    @NotNull
    private Timestamp timeEnd;
}
