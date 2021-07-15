package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.*;
import ru.dsr.itest.rest.response.QuestionView;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.QuestionService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Api.V1 + "/edit/quest")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PutMapping
    @ResponseStatus(OK)
    public void updateQuestion(@AuthenticationPrincipal AccountDetails details,
                               @RequestBody @Valid QuestionDto settings) {
        questionService.updateQuestionSettings(details.getId(), settings);
    }

    @GetMapping("/all/{testId}")
    @ResponseStatus(OK)
    public List<QuestionView> findAll(@AuthenticationPrincipal AccountDetails details,
                                      @PathVariable Integer testId) {
        return questionService.findAll(details.getId(), testId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public QuestionDto getSettings(@AuthenticationPrincipal AccountDetails details,
                                   @PathVariable Integer id) {
        return QuestionDto.from(questionService.getQuest(details.getId(), id), true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@AuthenticationPrincipal AccountDetails details,
                       @PathVariable Integer id) {
        questionService.deleteQuest(details.getId(), id);
    }
}
