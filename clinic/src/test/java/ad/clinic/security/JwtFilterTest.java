package ad.clinic.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.nimbusds.jose.proc.SecurityContext;

import ad.clinic.service.CustomDoctorDetailsService;
import ad.clinic.service.CustomPatientDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomDoctorDetailsService doctorDetailsService;

    @Mock
    private CustomPatientDetailsService patientDetailsService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    UserDetails userDetails;

    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        jwtFilter = new JwtFilter(jwtTokenProvider, doctorDetailsService, patientDetailsService);
    }   

    @Test
    void doFilterInternal_ValidToken_Doctor() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization"))); // Mock header names to include "Authorization
        when(jwtTokenProvider.validateToken("validToken")).thenReturn(true);
        when(jwtTokenProvider.extractUsername("validToken")).thenReturn("doctorUser");
        when(doctorDetailsService.loadUserByUsername("doctorUser")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList()); // Mock authorities if needed

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ValidToken_Patient() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization"))); // Mock header names to include "Authorization
        when(jwtTokenProvider.validateToken("validToken")).thenReturn(true);
        when(jwtTokenProvider.extractUsername("validToken")).thenReturn("patientUser");
        when(doctorDetailsService.loadUserByUsername("patientUser")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList()); // Mock authorities if needed

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);
    }
        
    @Test
    void doFilterInternal_InvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList())); 

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);

    
}

@Test 
void doFilterInternal_InvalidTokenFormat() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("InvalidTokenFormat");
    when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization"))); 

    jwtFilter.doFilterInternal(request, response, filterChain);

    assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain, times(1)).doFilter(request, response);
}


@Test
void doFilterInternal_TokenisOK_UserNotFound() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
    when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization"))); 
    when(jwtTokenProvider.validateToken("validToken")).thenReturn(true);
    when(jwtTokenProvider.extractUsername("validToken")).thenReturn("unknownUser");
    when(doctorDetailsService.loadUserByUsername("unknownUser")).thenThrow(new RuntimeException("User not found in doctor service"));
    lenient().when(patientDetailsService.loadUserByUsername("unknownUser")).thenThrow(new RuntimeException("User not found in patient service"));


    jwtFilter.doFilterInternal(request, response, filterChain);

    assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain, times(1)).doFilter(request, response);

}

@Test
void doFilterInternal_TokenisExtinct() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Bearer expiredToken");
    when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization"))); 
    when(jwtTokenProvider.validateToken("expiredToken")).thenReturn(false);

    jwtFilter.doFilterInternal(request, response, filterChain);

    assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain, times(1)).doFilter(request, response);

}



}