package ru.dsr.itest.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.dsr.itest.security.component.AccountDetailsService;
import ru.dsr.itest.security.details.AccountAuth;
import ru.dsr.itest.security.details.AccountDetails;

@Component
@RequiredArgsConstructor
public class JwtAuthLoader {
    private final AccountDetailsService detailsService;
    private final JwtProvider jwtProvider;

    public Authentication loadBy(String token) {
        Jws<Claims> claims = jwtProvider.parseClaims(token);
        if (claims == null) return null;
        String email = jwtProvider.getEmail(claims);
        AccountDetails details = detailsService.loadUserByUsername(email);
        if (details == null) return null;
        return new AccountAuth(details);
    }
}
