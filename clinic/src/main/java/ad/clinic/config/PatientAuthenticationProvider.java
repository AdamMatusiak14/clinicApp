package ad.clinic.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ad.clinic.service.CustomDoctorDetailsService;
import ad.clinic.service.CustomPatientDetailsService;


@Component
public class PatientAuthenticationProvider implements AuthenticationProvider {



      private final CustomPatientDetailsService patientDetailsService;
      private final PasswordEncoder passwordEncoder;
   
   
      public PatientAuthenticationProvider(CustomPatientDetailsService patientDetailsService, PasswordEncoder passwordEncoder) {
        this.patientDetailsService = patientDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

          UserDetails user;

        try{
        
            user = patientDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Bad credentials");}
            
    

             if (!passwordEncoder.matches(password, user.getPassword())) {
           throw new BadCredentialsException("Bad credentials matches");
    }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
    
    //   private Authentication createSuccesfulAuthentication(Authentication authentication, UserDetails user) {
    //     UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,
    //             null, user.getAuthorities());
    //     token.setDetails(authentication.getDetails());
    //     return token;
    // }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
    
}
