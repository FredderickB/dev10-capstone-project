package learn.blindchess.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import learn.blindchess.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtProvider{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        if (user == null || user.getUserId() == null || user.getUserId() == 0) {
            throw new IllegalArgumentException("User or User ID cannot be null/zero when generating token.");
        }

        return Jwts.builder()
                .subject(String.valueOf(user.getUserId()))
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateGuestToken(String guestId) {
        if (guestId == null || guestId.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest identifier cannot be null or empty.");
        }

        return Jwts.builder()
                .subject(guestId)
                .claim("roles", List.of("ROLE_GUEST"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object getPrincipalFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();

            if (subject == null || subject.trim().isEmpty() || "null".equalsIgnoreCase(subject.trim())) {
                return null;
            }

            String trimmedSubject = subject.trim();

            if (trimmedSubject.startsWith("GUEST_")) {
                return trimmedSubject;
            }

            try {
                return Integer.parseInt(trimmedSubject);
            } catch (NumberFormatException e) {
                return trimmedSubject;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getIntFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();

            if (subject == null || subject.trim().isEmpty() || "null".equalsIgnoreCase(subject.trim())) {
                return null;
            }

            return Integer.parseInt(subject.trim());
        } catch (Exception e) {
            return null;
        }
    }
}