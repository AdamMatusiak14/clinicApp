package ad.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ad.clinic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Możesz dodać dodatkowe metody wyszukiwania, jeśli potrzebujesz
    
}
