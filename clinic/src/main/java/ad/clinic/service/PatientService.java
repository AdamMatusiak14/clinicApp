package ad.clinic.service;

import org.springframework.stereotype.Service;

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
    
}
