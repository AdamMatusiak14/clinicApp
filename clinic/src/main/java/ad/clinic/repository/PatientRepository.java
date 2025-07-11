package ad.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.clinic.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Możesz dodać dodatkowe metody wyszukiwania, jeśli potrzebujesz
    
}
