package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.AuthDto;
import ru.dsr.itest.rest.response.TokenResponse;
import ru.dsr.itest.security.jwt.JwtProvider;
import ru.dsr.itest.service.AuthService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(Api.V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    @ResponseStatus(CREATED)
    public TokenResponse signUp(@RequestBody @Valid AuthDto signUp) {
        Account account = authService.register(signUp);
        return new TokenResponse(jwtProvider.generate(account));
    }

    @PostMapping("/sign-in")
    @ResponseStatus(OK)
    public TokenResponse signIn(@RequestBody @Valid AuthDto signIn) {
        Account account = authService.login(signIn);
        return new TokenResponse(jwtProvider.generate(account));
    }
}
