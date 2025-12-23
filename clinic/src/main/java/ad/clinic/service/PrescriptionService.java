package ad.clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ad.clinic.model.Prescription;
import ad.clinic.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;    

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }


    public List<Prescription> findByPatientId(Long patientId) {
        // Assuming the repository has a method to find prescriptions by patient ID
        return prescriptionRepository.findByPatientId(patientId);
    }       
    

    public void savePrescription(Prescription prescription) {
        prescriptionRepository.save(prescription);
    }
}
