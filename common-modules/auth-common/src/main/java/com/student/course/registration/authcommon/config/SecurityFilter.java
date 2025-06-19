package com.student.course.registration.authcommon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilter {

    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return converter;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
            http
                    .authorizeHttpRequests(auth ->auth
                            .requestMatchers("/api/v1/courses/**").hasRole("admin")
                            .anyRequest().authenticated());

            http
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(customAuthenticationEntryPoint)

                            .accessDeniedHandler(accessDeniedHandler)
                    )
                    .oauth2ResourceServer(oauth2 ->oauth2
                            .authenticationEntryPoint(customAuthenticationEntryPoint)
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                    );



            return http.build();
    }
}
