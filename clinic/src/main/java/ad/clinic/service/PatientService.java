package ad.clinic.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ad.clinic.DTO.PatientDTO;
import ad.clinic.model.Patient;
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

    public boolean verifyPatient(PatientDTO patientDTO) {
        // Sprawdza w bazie danych czy taki uzytkownik istnieje
        String firstName = patientDTO.getFirstName();
        String lastName = patientDTO.getLastName();
        Optional <Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(firstName, lastName);
        if (optionalPatient.isPresent()) {
            return true; // Pacjent istnieje
        } else {
            return false; // Pacjent nie istnieje
        }
    }
    
}
