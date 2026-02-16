package ad.clinic.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;

public class JwtTokenProviderTest {
    
    private JwtTokenProvider jwtTokenProvider; 

    @Test
    void generateToken_ShouldReturnNotNullToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER", 1L);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER", 1L);
        String username = jwtTokenProvider.extractUsername(token);
        assertNotNull(username);
        assert(username.equals("testuser"));
    }

    @Test
    void extractRoles_ShouldReturnCorrectRole() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER", 1L);
        String role = jwtTokenProvider.extractRoles(token);
        assertNotNull(role);
        assert(role.equals("ROLE_USER"));
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER", 1L);
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String invalidToken = "invalid.token.here";
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        assertFalse(isValid);
}

    @Test
    void validateToken_ShouldReturnFalseForExpiredToken() throws InterruptedException {
        jwtTokenProvider = new JwtTokenProvider();
        long expiredValidityInMs = -1000; // 1 sekundę
        SecretKey key = Jwts.SIG.HS256.key().build();
          
        String expiredToken = Jwts.builder()
                .subject("testuser")
                .claim("role", "ROLE_USER")
                .claim("id", 1L)
                .issuedAt(new Date(System.currentTimeMillis()-2000))
                .expiration(new Date(System.currentTimeMillis() - expiredValidityInMs)) // Token ważny tylko 1 sekundę
                .signWith(key, Jwts.SIG.HS256)
                .compact();
        
        boolean isValid = jwtTokenProvider.validateToken(expiredToken);
        assertFalse(isValid);
    }


}
