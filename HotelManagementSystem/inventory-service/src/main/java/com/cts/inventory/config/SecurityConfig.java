package com.cts.inventory.config;

import com.cts.inventory.security.JwtAuthenticationFilter;
import com.cts.inventory.security.JwtTokenValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true,
    proxyTargetClass = true
)
@Order(SecurityProperties.BASIC_AUTH_ORDER) // ensure this chain wins if multiple chains exist
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // If you can, prefer constructor injection in JwtAuthenticationFilter and pass this bean in
    @Bean
    public JwtTokenValidator jwtTokenValidator() {
        return new JwtTokenValidator();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(); // will be managed by Spring and autowired with JwtTokenValidator
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // health or actuator if you expose them
                .requestMatchers("/actuator/**").permitAll()

                // Rooms: READ for any authenticated user
                .requestMatchers(HttpMethod.GET, "/rooms/**").authenticated()
                // Rooms: CREATE only ADMIN
                .requestMatchers(HttpMethod.POST, "/rooms/add").hasAuthority("ROLE_ADMIN")

                // RoomTypes (adjust as you need)
                .requestMatchers(HttpMethod.GET, "/roomtypes/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/roomtypes/**").hasAuthority("ROLE_ADMIN")

                // Properties (adjust as you need)
                .requestMatchers(HttpMethod.GET, "/properties/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/properties/**").hasAuthority("ROLE_ADMIN")

                // Everything else
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults()); // convenient for quick testing (not used if JWT present)

        return http.build();
    }
}