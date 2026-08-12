package ad.clinic.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;

@Service
@Primary
public class CombinedUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CombinedUserDetailsService.class);

    private final PatientService patientService;
    private final DoctorService doctorService;

    public CombinedUserDetailsService(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Upewnij się, że username to to samo pole, którego używają repository (np. email)
        Optional<Patient> pat = patientService.findPatientByUsername(username);
        if (pat.isPresent()) {
            Patient patient = pat.get();
            log.debug("Znaleziono pacjenta: {}", patient.getEmail());
            return mapPatient(patient);
        }

        Optional<Doctor> doc = doctorService.findDoctorByUsername(username);
        if (doc.isPresent()) {
            Doctor doctor = doc.get();
            log.debug("Znaleziono lekarza: {}", doctor.getEmail());
            return mapDoctor(doctor);
        }

        log.debug("Nie znaleziono użytkownika: {}", username);
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    private UserDetails mapPatient(Patient p) {
       
        return User.builder()
                .username(p.getEmail())
                .password(p.getPassword())
                .roles(p.getRole())
                .build();
    }

    private UserDetails mapDoctor(Doctor d) {
       
        return User.builder()
                .username(d.getEmail())
                .password(d.getPassword())
                .roles(d.getRole())
                .build();
    }
}
