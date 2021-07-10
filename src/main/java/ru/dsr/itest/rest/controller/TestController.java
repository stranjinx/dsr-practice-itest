package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.request.NewTest;
import ru.dsr.itest.rest.request.TestDuration;
import ru.dsr.itest.rest.request.UpdateTest;
import ru.dsr.itest.rest.response.CreatedTest;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.TestService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Api.V1 + "/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping()
    @ResponseStatus(CREATED)
    public CreatedTest create(@AuthenticationPrincipal AccountDetails details,
                              @RequestBody @Valid NewTest test) {
        return testService.createTest(details.getId(), test.getDiscipline());
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Test get(@AuthenticationPrincipal AccountDetails details,
                     @PathVariable Integer id) {
        return testService.findTest(details.getId(), id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public void update(@AuthenticationPrincipal AccountDetails details,
                       @PathVariable Integer id,
                       @RequestBody @Valid UpdateTest updateData) {
        testService.updateTest(details.getId(), id, updateData);
    }

    @GetMapping("/main")
    @ResponseStatus(OK)
    public List<CreatedTest> main(@AuthenticationPrincipal AccountDetails details) {
        return testService.findMain(details.getId());
    }

    @PutMapping("/{id}/start")
    @ResponseStatus(OK)
    public void start(@AuthenticationPrincipal AccountDetails details,
                      @PathVariable Integer id,
                      @RequestBody @Valid TestDuration duration) {
        testService.startTest(details.getId(), id, duration);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@AuthenticationPrincipal AccountDetails details,
                      @PathVariable Integer id) {
        testService.deleteTest(details.getId(), id);
    }
}
