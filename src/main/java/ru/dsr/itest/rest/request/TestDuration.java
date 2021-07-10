package ru.dsr.itest.rest.request;

import lombok.Getter;
import ru.dsr.itest.rest.validation.Duration;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Duration(target = Duration.Target.FUTURE)
public class TestDuration {
    @NotNull
    private Timestamp timeStart;
    @NotNull
    private Timestamp timeEnd;
}
