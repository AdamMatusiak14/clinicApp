package ad.clinic.config;



import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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


@Component
public class DoctorAuthenticationProvider implements AuthenticationProvider {

    private final CustomDoctorDetailsService doctorDetailsService;
    private PasswordEncoder passwordEncoder;
    public DoctorAuthenticationProvider(CustomDoctorDetailsService doctorDetailsService, PasswordEncoder passwordEncoder) {
        this.doctorDetailsService = doctorDetailsService;
        this.passwordEncoder = passwordEncoder;  
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

    UserDetails user;

       try{
           user = doctorDetailsService.loadUserByUsername(username);
       } catch (UsernameNotFoundException e){
        throw new BadCredentialsException("Bad credentials");}
           
            System.out.println("Zawartość Usera " + user.getUsername().toString());
            System.out.println("Zawartość hasła z bazy: "+ user.getPassword().toString());
            System.out.println("Zawartość hasła z inputu: "+ password.toString());
            System.out.println("Password Encoder: " + passwordEncoder.encode(password));

           // String pass = passwordConfig.passwordEncoder().encode(password); To nie jest potrzebne


            System.out.println("Raw: " + password + " Encoded: " + user.getPassword());
            System.out.println("Match: " + passwordEncoder.matches(password, user.getPassword()));

           


         

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
