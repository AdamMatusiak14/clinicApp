package ad.clinic.service;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ad.clinic.DTO.PatientDTO;
import ad.clinic.DTO.patientDTO.PatientCredentailMapperDTO;
import ad.clinic.DTO.patientDTO.PatientCredentialDTO;
import ad.clinic.model.Patient;
import ad.clinic.model.PatientData;
import ad.clinic.repository.PatientRepository;

@Service
public class PatientService {

   private PatientRepository patientRepository;
   private PasswordEncoder  passwordEncoder;

   public PatientService(PatientRepository patientRepository, PasswordEncoder  passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }   



   public void registerPatient(Patient patient) {
        patient.setRole("PATIENT");
        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);
        patientRepository.save(patient);
    }

    public Patient verifyPatient(PatientDTO patientDTO) {
        // Sprawdza w bazie danych czy taki uzytkownik istnieje
        String firstName = patientDTO.getFirstName();
        String lastName = patientDTO.getLastName();
        Optional <Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(firstName, lastName);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return patient; // Pacjent istnieje
        } else {
            return null; // Pacjent nie istnieje
        }
    }

    public Patient findPatient (PatientDTO patientDTO) {
        String firstName = patientDTO.getFirstName();
        String lastName = patientDTO.getLastName();
        Optional<Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(firstName, lastName);
        if (optionalPatient.isPresent()) {
            return optionalPatient.get(); // Pacjent istnieje
        } else {
            return null; // Pacjent nie istnieje
        }
       
       
       
    }

      

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElse(null);
    } 

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findPatientByfirstNameAndLastName(String firstName, String lastName) {
        Optional<Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(firstName, lastName);
        if (optionalPatient.isPresent()) {
            return optionalPatient.get(); // Pacjent istnieje
        } else {
            return null; // Pacjent nie istnieje
        }
    }
    

   public Optional<Patient> findPatientByUsername(String username) {
       return patientRepository.findPatientByEmail(username);  
    }

    public Patient findPatientByEmail(String email) {
        Optional<Patient> optionalPatient = patientRepository.findPatientByEmail(email);
        return optionalPatient.orElse(null);
    }

    public Patient findByUsername(String username) {  
        return patientRepository.findPatientByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found with username: " + username));
    }   

    public List<PatientDTO> getAllPatients() {
        Iterable<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patients) {
            PatientDTO dto = new PatientDTO();
            dto.setId(patient.getId());
            dto.setFirstName(patient.getFirstName());
            dto.setLastName(patient.getLastName());
            patientDTOs.add(dto);
        }
        return patientDTOs;
    }


    
}
