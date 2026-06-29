package ad.clinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.security.AuthRequest;
import ad.clinic.security.AuthResponse;
import ad.clinic.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
   

    public AuthController(AuthService authService)  {
        this.authService = authService;
    }   
// Rzuca błąd i trzeba go zniwelować jest w Chat 

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        logger.debug("Login attempt for email {}", request.getEmail());

        try {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException ex) {
            logger.warn("Nieprawidłowe dane logowania: {}", ex.getMessage());
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of("message", ex.getMessage()));
        } catch (Exception e) {
            // Inne błędy
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("message", "Wystąpił błąd"));
        }
    }


    
}
