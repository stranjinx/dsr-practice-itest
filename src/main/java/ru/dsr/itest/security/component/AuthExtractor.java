package ru.dsr.itest.security.component;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Component
public class AuthExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEGIN = "Bearer";

    public String extract(ServletRequest request) {
        return (request instanceof HttpServletRequest) ? extract((HttpServletRequest) request) : null;
    }

    public String extract(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        return token != null && token.startsWith(BEGIN) ? token.substring(BEGIN.length() + 1) : null;
    }
}
