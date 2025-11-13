package ad.clinic.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

   public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }   



   public void registerPatient(Patient patient) {
        patient.setRole("ROLE_PATIENT");
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

    public Patient findPatientById(Long id) {
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
}
