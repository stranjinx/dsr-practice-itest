package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Student;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.StudentDto;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.AccountService;
import ru.dsr.itest.service.StudentService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Api.V1 + "/student")
@RequiredArgsConstructor
public class StudentController {
    private StudentService accountService;

    @PostMapping
    @ResponseStatus(OK)
    public void pushProfile(@AuthenticationPrincipal AccountDetails details,
                            @RequestBody @Valid StudentDto dto) {
        accountService.updateProfile(details.getId(), dto);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Student getMainProfile(@AuthenticationPrincipal AccountDetails details) {
        return accountService.getProfile(details.getId());
    }
}
