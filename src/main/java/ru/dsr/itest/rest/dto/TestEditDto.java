package ru.dsr.itest.rest.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class TestEditDto {
    @NotBlank
    @Size(max = 256)
    private String title;
}
