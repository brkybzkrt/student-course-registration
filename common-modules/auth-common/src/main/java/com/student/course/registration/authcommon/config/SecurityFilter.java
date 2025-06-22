package com.student.course.registration.authcommon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


    @Autowired
    KeycloakConfiguration confs;

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return converter;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
            http
//                    .cors(Customizer.withDefaults())
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth ->auth
                            .requestMatchers("/api/v1/courses/**").hasRole(confs.getAdminRole())
                            .requestMatchers("/api/v1/students/**").permitAll()
                            .requestMatchers("api/v1/admins/**").hasRole(confs.getAdminRole())
                            .requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/docs"
                            ).permitAll()
                            .requestMatchers("/api/v1/registrations/**").permitAll()
                            .anyRequest().authenticated())

                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(customAuthenticationEntryPoint)

                            .accessDeniedHandler(accessDeniedHandler)
                    )
                    .oauth2ResourceServer(oauth2 ->oauth2
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                    );



            return http.build();
    }
}
