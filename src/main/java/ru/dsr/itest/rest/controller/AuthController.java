package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.request.AuthData;
import ru.dsr.itest.rest.response.TokenResponse;
import ru.dsr.itest.security.jwt.JwtProvider;
import ru.dsr.itest.service.AuthService;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(Api.V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    @PostMapping("/sign-up")
    @ResponseStatus(CREATED)
    public TokenResponse signUp(@RequestBody @Valid AuthData signUp) {
        String encodedPass = encoder.encode(signUp.getPassword());
        Optional<Account> created = authService.register(new Account(signUp.getEmail(), encodedPass));
        if (!created.isPresent())
            throw new BadCredentialsException("EMAIL EXISTS");
        String token = jwtProvider.generate(created.get());
        return new TokenResponse(token);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(OK)
    public TokenResponse signIn(@RequestBody @Valid AuthData signIn) {
        Optional<Account> o = authService.findAccountByEmail(signIn.getEmail());
        if (!o.isPresent()) throw new BadCredentialsException("WRONG EMAIL");
        if (!validate(signIn, o.get())) throw new BadCredentialsException("WRONG PASSWORD");
        return new TokenResponse(jwtProvider.generate(o.get()));
    }

    private boolean validate(AuthData signIn, Account account) {
        return encoder.matches(signIn.getPassword(), account.getPassword());
    }
}
