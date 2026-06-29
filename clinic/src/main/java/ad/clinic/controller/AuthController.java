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
import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
   

    public AuthController(AuthService authService)  {
        this.authService = authService;
    }   
// Rzuca błąd i trzeba go zniwelować jest w Chat 

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        System.out.println("Jestem kontrolerem authController");

        System.out.println("Name " + request.getEmail());
        System.out.println("Password " + request.getPassword());
        System.out.println("ID " + request.getId());

        try {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException ex) {
            // Nieprawidłowe dane logowania - zwróć przyjazny komunikat JSON
            System.out.println("Nieprawidłowe dane logowania: " + ex.getMessage());
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of("message", ex.getMessage()));
        } catch (Exception e) {
            // Inne błędy
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("message", "Wystąpił błąd"));
        }
    }


    
}
