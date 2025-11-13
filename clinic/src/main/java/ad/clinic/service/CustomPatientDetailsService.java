package ad.clinic.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ad.clinic.DTO.patientDTO.PatientCredentialDTO;
import ad.clinic.model.Patient;


@Service
public class CustomPatientDetailsService implements UserDetailsService {

   private final PatientService patientService;

    public CustomPatientDetailsService(PatientService patientService) {
        this.patientService = patientService;
    }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
         Optional<Patient> pat =  patientService.findPatientByUsername(username);
         if(pat.isPresent()){
          Patient patient = pat.get();
          System.out.println("Patient w CustomPatientDetailsService: " + patient.getEmail());
          return mapToUserDetails(patient);
         } else{
            throw new UsernameNotFoundException("Patient not found with username: " + username);
         }
        }

    private UserDetails mapToUserDetails(Patient patient) {
        return User.builder()
                .username(patient.getEmail())
                .password(patient.getPassword())
                .roles(patient.getRole()) 
                .build();
    }
  }
    

