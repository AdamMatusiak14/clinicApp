package ad.clinic.controller;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import ad.clinic.model.Patient;
import ad.clinic.security.AuthRequest;
import ad.clinic.security.JwtTokenProvider;
import ad.clinic.service.DoctorService;
import ad.clinic.service.PatientService;
import jakarta.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    DoctorService doctorService;

    @Mock
    PatientService patientService;

    @Mock
    JwtTokenProvider jwtTokenProvider;


    @InjectMocks
    AuthController authController;


    AuthRequest authRequest = new AuthRequest("user@mail.com", "password", 1L);

    UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername("user@mail.com")
            .password("password")
            .roles("PATIENT")
            .build();

    @Test
    void loginSuccess_returnsToken() throws Exception {


        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

       
                Patient patient = new Patient(1L, "John", "Doe");
                when(patientService.findPatientByUsername("user@mail.com")).thenReturn(Optional.of(patient));

                when(jwtTokenProvider.generateToken("user@mail.com", "ROLE_PATIENT", 1L)).thenReturn("token123");

               ResponseEntity<?> response = authController.login(authRequest);

        assert(response.getStatusCode().is2xxSuccessful());
        assert(response.getBody() instanceof ad.clinic.security.AuthResponse);

        verify(authenticationManager, times(1)).authenticate(any());
        verify(patientService, times(1)).findPatientByUsername("user@mail.com");
        verify(jwtTokenProvider, times(1)).generateToken("user@mail.com", "ROLE_PATIENT", 1L);
        
        
        
    }

    @Test
    void login_Failure_InvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));


        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authController.login(authRequest);
        });
        
        assertEquals("Bad credentials", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any());
}


@Test
void login_Failure_UserDetailsNotRole() throws Exception {
   
AuthRequest authRequest = new AuthRequest("user@mail.com", "pass", null);
UserDetails userDetails = org.springframework.security.core.userdetails.User
        .withUsername("user@mail.com")
        .password("pass")
        .authorities(Collections.emptyList())
        .build();

Authentication auth = mock(Authentication.class);
when(auth.getPrincipal()).thenReturn(userDetails);

when(authenticationManager.authenticate(any()))
        .thenReturn(auth);

when(jwtTokenProvider.generateToken("user@mail.com", "ROLE_USER", null)).thenReturn("token123");

ResponseEntity<?> response = authController.login(authRequest);

assert(response.getStatusCode().is2xxSuccessful());
assert(response.getBody() instanceof ad.clinic.security.AuthResponse);


}


@Test
void login_Failure_PatientNotFound() throws Exception {
       Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

       
                //Patient patient = new Patient(1L, "John", "Doe");
                when(patientService.findPatientByUsername("user@mail.com")).thenReturn(Optional.empty());

              RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                  authController.login(authRequest);
              });

        assertEquals("Patient not found", exception.getMessage());


}

@Test
void login_Failure_DoctorNotFound() throws Exception {

 UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername("user@mail.com")
            .password("password")
            .roles("DOCTOR")
            .build();

       Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

       
                //Patient patient = new Patient(1L, "John", "Doe");
                when(doctorService.findDoctorByUsername("user@mail.com")).thenReturn(Optional.empty());

              RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                  authController.login(authRequest);
              });

        assertEquals("Doctor not found", exception.getMessage());


}



}


    

