package ad.clinic.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        this.patientDetailsService = patientDetailsService;
      
    }   

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

         System.out.println("Nagłówki: " + Collections.list(request.getHeaderNames())); // Brakuje nagłówka Authorization
        String authHeader = request.getHeader("Authorization"); // Heder się nie wyświtla   

       
        System.out.println("JwtFilter - Authorization Header: " + authHeader); 

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // usuwa "Bearer "
            if(jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.extractUsername(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                System.out.println("JwtFilter - Extracted Username: " + username);
            UserDetails userDetails = null;
                try{
             userDetails = doctorDetailsService.loadUserByUsername(username);
                } catch (UsernameNotFoundException e){
                if(patientDetailsService != null){ 
                    try{
                     userDetails = patientDetailsService.loadUserByUsername(username);
                    } catch (UsernameNotFoundException ex){
                        System.out.println("JwtFilter - User not found in both doctor and patient services");
                    }
                }else{
                    System.out.println("JwtFilter - PatientDetailsService is null");   
                }
                }catch (Exception e){
                    System.out.println("JwtFilter - Exception while loading user details: " + e.getMessage());
                }

           
            

                if(userDetails != null){
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());                    
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        
      
    }

         System.out.print("doFilterInternal - start");
        filterChain.doFilter(request, response); // idziemy dalej
        System.out.print("doFilterInternal - stop");

}
}
    

