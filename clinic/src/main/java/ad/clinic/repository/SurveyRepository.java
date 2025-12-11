package ad.clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.clinic.model.PatientData;

@Repository
public interface SurveyRepository extends JpaRepository <PatientData, Long> {



     
    
}
