package com.travelease.reservation.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class JwtTokenValidator {

    private static final String JWT_SECRET = "mySecretKeyForHotelManagementSystemThatIsLongEnoughToBeSecureAndCanBeUsedForHS512";

    private Claims parseClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token) {
        try {
            return parseClaims(token).getSubject(); // 'sub'
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Error extracting email (sub) from token", e);
            return null;
        }
    }

    public Long getUserIdFromToken(String token) {
        try {
            Object v = parseClaims(token).get("userId");
            if (v == null) return null;
            if (v instanceof Number n) return n.longValue();
            return Long.valueOf(String.valueOf(v));
        } catch (Exception e) {
            log.error("Error extracting userId from token", e);
            return null;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            Claims claims = parseClaims(token);

            // Try multiple keys: role, role_admin, roleAdmin, roles (array or string)
            Object roleClaim = claims.get("role");
            if (roleClaim == null) roleClaim = claims.get("role_admin");
            if (roleClaim == null) roleClaim = claims.get("roleAdmin");
            if (roleClaim == null) roleClaim = claims.get("roles");

            String role = null;

            if (roleClaim instanceof String s) {
                role = s;
            } else if (roleClaim instanceof Collection<?> c && !c.isEmpty()) {
                role = String.valueOf(c.iterator().next()); // take first, e.g. "ADMIN"
            } else if (roleClaim instanceof Object[] arr && arr.length > 0) {
                role = String.valueOf(arr[0]);
            } else if (roleClaim instanceof Map<?,?> m && m.get("authority") != null) {
                role = String.valueOf(m.get("authority"));
            }

            if (role != null) role = role.trim();

            log.debug("Extracted role claim value before prefix normalization: {}", role);
            return role;
        } catch (Exception e) {
            log.error("Error extracting role from token", e);
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token); // parses + verifies signature/exp
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}