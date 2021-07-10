package ru.dsr.itest.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccountAuthEntryPoint implements AuthenticationEntryPoint {
    private static final String HEADER = "WWW-Authenticate";
    private static final String VALUE = "Bearer realm=";
    private static final String MSG = "Unauthorized";

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.addHeader(HEADER, VALUE);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MSG);
    }
}
