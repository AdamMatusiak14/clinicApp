package ad.clinic.security;

    
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long validityInMs = 3600000; // 1h

    public String generateToken(String username, String role, Long id) {
     return Jwts.builder()
        .subject(username)
        .claim("role", role)
        .claim("id", id)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + validityInMs))
        .signWith(key, Jwts.SIG.HS256) // Trzeba jawnie podać algorytm
        .compact();
    }

    public String extractUsername(String token) { 
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("Token niepoprawny: " + ex.getMessage());
            return false;
        }
    }

    public String extractRoles(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}

