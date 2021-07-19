package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Discipline;
import ru.dsr.itest.db.entity.Response;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.AnswersDto;
import ru.dsr.itest.rest.dto.ResponseDto;
import ru.dsr.itest.rest.response.TestExamInfoView;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.ExamService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Api.V1 + "/exam")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping
    @ResponseStatus(OK)
    public List<TestExamInfoView> getAvailable(@RequestParam(required = false) Discipline discipline) {
        return discipline == null ?
                examService.getAllAvailable() :
                examService.getAllAvailable(discipline);
    }

    @GetMapping("/{test}")
    @ResponseStatus(OK)
    public ResponseDto findTestResponse(@AuthenticationPrincipal AccountDetails details,
                                        @PathVariable Integer test) {
        return ResponseDto.from(examService.findResponse(details.getId(), test));
    }

    @PutMapping("/{test}")
    @ResponseStatus(OK)
    public void updateTestResponseAnswers(@AuthenticationPrincipal AccountDetails details,
                                          @PathVariable Integer test,
                                          @RequestBody @Valid AnswersDto answers) {
        examService.saveResponseAnswers(details.getId(), test, answers);
    }
}
