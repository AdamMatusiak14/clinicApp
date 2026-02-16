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

import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;
import ad.clinic.security.AuthRequest;
import ad.clinic.security.AuthResponse;
import ad.clinic.security.JwtTokenProvider;
import ad.clinic.service.DoctorService;
import ad.clinic.service.PatientService;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final DoctorService doctorService;
    private final PatientService patientService;
   

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, DoctorService doctorService, PatientService patientService)  {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println("Jestem kontrolerem authController");

        System.out.println("Name " + request.getemail());
        System.out.println("Password " + request.getPassword());
        System.out.println("ID " + request.getId());

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

        Long userId = null;
        if (role.equals("ROLE_DOCTOR")) {
            Doctor doctor =  doctorService.findDoctorByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Doctor not found"));
            userId = doctor.getId();
        } else if (role.equals("ROLE_PATIENT")) {
            Patient patient =  patientService.findPatientByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Patient not found"));
            userId = patient.getId();

        }

        String token = jwtTokenProvider.generateToken(userDetails.getUsername(), role, userId );


        return ResponseEntity.ok(new AuthResponse(token));
    }


    
}
