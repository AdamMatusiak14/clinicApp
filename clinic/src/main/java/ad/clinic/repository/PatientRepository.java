package ad.clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.clinic.DTO.PatientDTO;
import ad.clinic.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
    
    // Add any other necessary query methods here
   
    
}
