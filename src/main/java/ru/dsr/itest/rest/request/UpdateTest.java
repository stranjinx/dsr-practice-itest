package ru.dsr.itest.rest.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateTest {
    @NotBlank
    private String title;
}
