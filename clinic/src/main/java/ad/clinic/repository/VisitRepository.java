package ad.clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.clinic.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    // Define custom query methods if needed
public List<Visit> findByPatientId(Long id);
    // Example: List<Visit> findByPatientId(Long patientId);

public List<Visit> findByDoctorIdAndDate(Long doctorId, java.time.LocalDate date);     

public List<Visit> findByDoctorId(Long doctorId);
    
} 
