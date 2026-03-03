package com.cts.registry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())         // Eureka clients use POST/PUT; disable CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()         // Allow everything (dashboard + /eureka/**)
            )
            .httpBasic(Customizer.withDefaults())  // No effect since everything is permitted
            .formLogin(form -> form.disable());    // Disable login form

        return http.build();
    }
}