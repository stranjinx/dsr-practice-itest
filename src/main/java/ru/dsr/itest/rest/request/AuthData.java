package ru.dsr.itest.rest.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class AuthData {
    @Email @NotBlank
    private String email;
    @Size(min = 8, max = 64)
    private String password;
}
