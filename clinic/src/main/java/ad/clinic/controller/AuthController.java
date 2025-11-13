package ad.clinic.controller;

import java.security.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.security.AuthRequest;
import ad.clinic.security.AuthResponse;
import ad.clinic.security.JwtTokenProvider;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
   

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider)  {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
      
      
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println("Jestem kontrolerem authController");

        System.out.println("Name " + request.getemail());
        System.out.println("Password " + request.getPassword());

        // AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getemail(), request.getPassword()) 
        );
         System.out.println("Po autentykacji");

         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("ROLE_USER"); // Domyślna rola, jeśli nie znaleziono    
        String token = jwtTokenProvider.generateToken(userDetails.getUsername(), role);


        return ResponseEntity.ok(new AuthResponse(token));
    }


    
}
