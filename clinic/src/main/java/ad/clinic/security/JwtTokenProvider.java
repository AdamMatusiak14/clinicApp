package ad.clinic.security;

    
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private long validityInMs;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    // private final SecretKey key = Jwts.SIG.HS256.key().build();
    // private final long validityInMs = 3600000; // 1h

    public String generateToken(String username, String role, Long id) {
     return Jwts.builder()
        .subject(username)
        .claim("role", role)
        .claim("id", id)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + validityInMs))
        .signWith(getKey(), Jwts.SIG.HS256) // Trzeba jawnie podać algorytm
        .compact();
    }

    public String extractUsername(String token) { 
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.warn("Token niepoprawny: {}", ex.getMessage());
            return false;
        }
    }

    public String extractRoles(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}

