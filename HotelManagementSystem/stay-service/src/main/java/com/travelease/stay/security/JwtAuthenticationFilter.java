package com.travelease.stay.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenValidator.validateToken(jwt)) {

                String email = tokenValidator.getEmailFromToken(jwt);
                String role  = tokenValidator.getRoleFromToken(jwt);
                Long userId  = tokenValidator.getUserIdFromToken(jwt);

                // See what came from the token
                log.debug("STAY SERVICE → JWT claims: sub(email)={}, userId={}, role(raw)={}", email, userId, role);

                // Normalize role to ROLE_*
                if (role != null && !role.startsWith("ROLE_")) {
                    role = "ROLE_" + role;
                }

                if (email != null && role != null) {
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("STAY SERVICE → AUTH SET WITH ROLE = {}", role);
                } else {
                    log.debug("STAY SERVICE → email or role is null; skipping authentication");
                }
            } else {
                log.debug("STAY SERVICE → TOKEN MISSING OR INVALID");
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context (stay)", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}