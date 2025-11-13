package ad.clinic.service;


import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ad.clinic.DTO.doctorDTO.DoctorCredentialDTO;
import ad.clinic.DTO.doctorDTO.DoctorCredentialMapperDTO;
import ad.clinic.model.Doctor;

@Service
public class CustomDoctorDetailsService implements UserDetailsService {

    private final DoctorService doctorService;

    public CustomDoctorDetailsService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
         Optional<Doctor> doc =  doctorService.findDoctorByUsername(username);
         if(doc.isPresent()){
          Doctor doctor = doc.get();
          System.out.println("Doctor w CustomDoctorDetailsService: " + doctor.getEmail());
          return mapToUserDetails(doctor);
         } else{
            throw new UsernameNotFoundException("Doctor not found with username: " + username);
         }
        
        
      //  return doctorService.findDoctorByUsername(username) 
      //    .map(this::mapToUserDetails);
       // .orElseThrow(() -> new UsernameNotFoundException("Doctor not found with username: " + username));
       
      
} 

private UserDetails mapToUserDetails(Doctor doctor) {
        return User.builder()
                .username(doctor.getEmail())
                .password(doctor.getPassword())
                .roles(doctor.getRole()) 
                .build();
    }
}
