package com.student.course.registration.base.interceptors.requestPath;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestPathInterceptor requestPathInterceptor;

    public WebConfig(RequestPathInterceptor requestPathInterceptor) {
        this.requestPathInterceptor = requestPathInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestPathInterceptor)
                .addPathPatterns("/**");
    }
}