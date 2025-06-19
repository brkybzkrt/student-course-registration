package com.student.course.registration.authcommon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.course.registration.base.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ErrorResponseUtil errorResponseUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ErrorResponse<String> error = errorResponseUtil.createErrorResponse(
                "You need a valid token",
                HttpServletResponse.SC_UNAUTHORIZED,
                null
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");



        String json = objectMapper.writeValueAsString(error);
        response.getWriter().write(json);
    }
}