package ad.clinic.service;

import org.springframework.stereotype.Service;

import ad.clinic.model.PatientData;
import ad.clinic.repository.SurveyRepository;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public String saveSurveyNote(PatientData note) {
       surveyRepository.save(note);
        return "Zapisano poprawnie";
    }
    
}
