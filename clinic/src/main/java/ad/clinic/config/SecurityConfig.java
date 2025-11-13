package ad.clinic.config;

import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import ad.clinic.service.CustomDoctorDetailsService;
import ad.clinic.service.CustomPatientDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private DoctorAuthenticationProvider doctorAuthenticationProvider; 
    private PatientAuthenticationProvider patientAuthenticationProvider; 
    private PasswordEncoder passwordEncoder;
    private CustomDoctorDetailsService customDoctorDetailsService; 
    private CustomPatientDetailsService customPatientDetailsService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider,  
    DoctorAuthenticationProvider doctorAuthenticationProvider, PatientAuthenticationProvider patientAuthenticationProvider, 
    PasswordEncoder passwordEncoder, CustomDoctorDetailsService customDoctorDetailsService, CustomPatientDetailsService customPatientDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.doctorAuthenticationProvider = doctorAuthenticationProvider; 
        this.patientAuthenticationProvider = patientAuthenticationProvider;
        this.passwordEncoder = passwordEncoder;
        this.customDoctorDetailsService = customDoctorDetailsService;
        this.customPatientDetailsService = customPatientDetailsService;
    }   
   

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
     
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();
      http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(h2ConsoleRequestMatcher).permitAll()
            .requestMatchers("/konsola-h2/**").permitAll()
            .requestMatchers("/patient", "/registration", "/verfication").permitAll()
            .requestMatchers("/doctor").permitAll()
            .requestMatchers("/assistent").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            //.requestMatchers("/ws/**/**").permitAll()
            .anyRequest().authenticated()
        )
         .addFilterBefore(new JwtFilter(jwtTokenProvider, customDoctorDetailsService, customPatientDetailsService ), UsernamePasswordAuthenticationFilter.class)
         .authenticationManager(authenticationManager)
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

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.authenticationProvider(doctorAuthenticationProvider);
        authBuilder.authenticationProvider(patientAuthenticationProvider);
    

        return authBuilder.build();
       
    }


   


}