package ru.dsr.itest.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dsr.itest.db.entity.Account;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.time.LocalDate.now;

@Component
public class JwtProvider {
    private static final int TOKEN_EXPIRATION = 30;
    @Value("$(jwt.secret)")
    private String secret;

    public Jws<Claims> parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmail(Jws<Claims> claims) {
        return claims.getBody().getSubject();
    }

    public String generate(Account account) {
        Date date = Date.from(now()
                .plusDays(TOKEN_EXPIRATION)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        return Jwts.builder()
                .setSubject(account.getEmail())
                .addClaims(new HashMap<String, Object>(){{
                    put("id", account.getId());
                    put("role", account.getRole());
                }})
                .setExpiration(date)
                .signWith(HS512, secret)
                .compact();
    }
}
