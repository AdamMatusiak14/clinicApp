package ad.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.clinic.model.PatientData;

@Repository
public interface SurveyRepository extends JpaRepository <PatientData, Long> {
    
    // Additional query methods can be defined here if needed
    // For example, to find
    
}
