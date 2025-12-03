package ad.clinic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ad.clinic.model.PatientData;
import ad.clinic.repository.SurveyRepository;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public String saveSurveyNote(PatientData record) {
       surveyRepository.save(record);
        return "Zapisano poprawnie";
    }

    public PatientData getSurveyByID(Long id) {
       return surveyRepository.findById(id).orElseThrow(() -> new RuntimeException("Survey not found"));
      
    
}
}