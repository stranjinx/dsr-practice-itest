package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.TestCreateDto;
import ru.dsr.itest.rest.dto.TestEditDto;
import ru.dsr.itest.rest.dto.TestDurationDto;
import ru.dsr.itest.rest.response.TestView;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.TestService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Api.V1 + "/edit/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Test create(@AuthenticationPrincipal AccountDetails details,
                       @RequestBody @Valid TestCreateDto testData) {
        return testService.createTest(details.getId(), testData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public void updateSettings(@AuthenticationPrincipal AccountDetails details,
                               @PathVariable Integer id,
                               @RequestBody @Valid TestEditDto testData) {
        testService.updateTestSettings(details.getId(), id, testData);
    }

    @PutMapping("/{id}/start")
    @ResponseStatus(OK)
    public void start(@AuthenticationPrincipal AccountDetails details,
                      @PathVariable Integer id,
                      @RequestBody @Valid TestDurationDto duration) {
        testService.start(details.getId(), id, duration);
    }

    @PutMapping("/{id}/stop")
    @ResponseStatus(OK)
    public void stop(@AuthenticationPrincipal AccountDetails details,
                      @PathVariable Integer id) {
        testService.stop(details.getId(), id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Test get(@AuthenticationPrincipal AccountDetails details,
                    @PathVariable Integer id) {
        return testService.findTest(details.getId(), id);
    }

    @GetMapping("/all")
    @ResponseStatus(OK)
    public List<TestView> findAll(@AuthenticationPrincipal AccountDetails details) {
        return testService.findAll(details.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@AuthenticationPrincipal AccountDetails details,
                      @PathVariable Integer id) {
        testService.deleteTest(details.getId(), id);
    }
}
