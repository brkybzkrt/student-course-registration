package com.student.course.registration.authcommon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.course.registration.base.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ErrorResponseUtil errorResponseUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        ErrorResponse<String> error = errorResponseUtil.createErrorResponse(
                "You don't have access to get this resource",
                HttpServletResponse.SC_FORBIDDEN,
                null
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");


        String json = objectMapper.writeValueAsString(error);
        response.getWriter().write(json);
    }
}