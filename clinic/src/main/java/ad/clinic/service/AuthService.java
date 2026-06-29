package ad.clinic.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ad.clinic.controller.AuthController;
import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;
import ad.clinic.security.AuthRequest;
import ad.clinic.security.AuthResponse;
import ad.clinic.security.JwtTokenProvider;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AuthService(@Lazy AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       DoctorService doctorService,
                       PatientService patientService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public AuthResponse authenticate(AuthRequest request) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Nieprawidłowy login lub hasło", e);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("ROLE_USER");

        Long userId = null;
        if (role.equals("ROLE_DOCTOR")) {
            Doctor doctor = doctorService.findDoctorByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            userId = doctor.getId();
        } else if (role.equals("ROLE_PATIENT")) {
            Patient patient = patientService.findPatientByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            userId = patient.getId();
        }

        String token = jwtTokenProvider.generateToken(userDetails.getUsername(), role, userId);
        return new AuthResponse(token);
    }
}
