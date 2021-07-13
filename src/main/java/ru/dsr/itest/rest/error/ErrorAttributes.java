package ru.dsr.itest.rest.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        return super.getErrorAttributes(webRequest, options.including(MESSAGE));
    }
}
