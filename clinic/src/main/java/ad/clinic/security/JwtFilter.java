package ad.clinic.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import ad.clinic.service.CustomDoctorDetailsService;
import ad.clinic.service.CustomPatientDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private CustomDoctorDetailsService doctorDetailsService;
    private CustomPatientDetailsService patientDetailsService;

    public JwtFilter(JwtTokenProvider jwtTokenProvider, CustomDoctorDetailsService doctorDetailsService, CustomPatientDetailsService patientDetailsService  ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.doctorDetailsService = doctorDetailsService;
      
    }   

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        System.out.println("JwtFilter - Authorization Header: " + authHeader); 

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // usuwa "Bearer "
            if(jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.extractUsername(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                System.out.println("JwtFilter - Extracted Username: " + username);
            UserDetails userDetails = doctorDetailsService.loadUserByUsername(username);
            if(userDetails == null){
             userDetails = patientDetailsService.loadUserByUsername(username);
            }
            

            // sprawdzamy czy jeszcze nie mamy Authentication w kontekście
            // if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //     if (jwtTokenProvider.validateToken(token)) {
                    // tworzymy Authentication z username i rolami
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());                    
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response); // idziemy dalej
    }
}
    

