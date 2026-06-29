package ad.clinic.config;

import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import ad.clinic.security.JwtFilter;
import ad.clinic.security.JwtTokenProvider;
import ad.clinic.service.CombinedUserDetailsService;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //private final JwtTokenProvider jwtTokenProvider;
   // private DoctorAuthenticationProvider doctorAuthenticationProvider; 
   // private PatientAuthenticationProvider patientAuthenticationProvider; 
    // private CustomDoctorDetailsService customDoctorDetailsService; 
    // private CustomPatientDetailsService customPatientDetailsService;
    public CombinedUserDetailsService userDetailsService;

    public SecurityConfig(CombinedUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

  
    @Bean
    public DaoAuthenticationProvider daoAuthProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


   

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                  AuthenticationManager authenticationManager,
                                                  DaoAuthenticationProvider daoAuthProvider,
                                                  JwtFilter jwtFilter
                                                  ) throws Exception {
     
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();
      http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(h2ConsoleRequestMatcher).permitAll()
            .requestMatchers("/konsola-h2/**").permitAll()
            .requestMatchers("/patient", "/registration", "/verfication").permitAll()
            .requestMatchers("/doctor").permitAll()
            .requestMatchers("/assistant/description").permitAll() // Tu zmieniałeś
            .requestMatchers("/api/doctors").permitAll()
            .requestMatchers("/uploads/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/patient/register").permitAll()
            .requestMatchers("/api/patient/**").hasAnyRole("PATIENT", "DOCTOR")
            .requestMatchers("/api/prescription/**").hasAnyRole("PATIENT", "DOCTOR")
            .requestMatchers("/api/doctor/**").hasRole("DOCTOR")
            .requestMatchers("/api/visit/**").hasRole("DOCTOR")
           
           // .requestMatchers("/api/patient/survey").hasRole("PATIENT")
            //.requestMatchers("/ws/**/**").permitAll()
            .anyRequest().authenticated()
        )
         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
         .authenticationManager(authenticationManager)
         .authenticationProvider(daoAuthProvider)
         .headers(headers-> headers.frameOptions().disable())   
         .cors(cors -> cors.configurationSource(request -> { 
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:3000"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
            config.setAllowCredentials(true);
            return config;
        }) 
        );
    
        return http.build();
    }

    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
    //     AuthenticationManagerBuilder authBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

    //     // authBuilder.authenticationProvider(doctorAuthenticationProvider);
    //     // authBuilder.authenticationProvider(patientAuthenticationProvider);
    //     authBuilder.userDetailsService(customDoctorDetailsService)
    

    //     return authBuilder.build();
       
    // } 


   


}