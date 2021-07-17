package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Student;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.StudentDto;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.StudentService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Api.V1 + "/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService accountService;

    @PutMapping
    @ResponseStatus(OK)
    public void updateMainProfile(@AuthenticationPrincipal AccountDetails details,
                                  @RequestBody @Valid StudentDto dto) {
        accountService.updateStudentProfile(details.getId(), dto);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Student getMainProfile(@AuthenticationPrincipal AccountDetails details) {
        return accountService.getStudentProfile(details.getId());
    }
}
