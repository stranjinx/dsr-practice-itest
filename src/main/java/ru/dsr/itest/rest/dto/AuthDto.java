package ru.dsr.itest.rest.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class AuthDto {
    @Email @NotBlank
    @Size(max = 256)
    private String email;
    @Size(min = 8, max = 256)
    private String password;
}
