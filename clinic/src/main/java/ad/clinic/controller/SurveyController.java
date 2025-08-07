package ad.clinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.model.Patient;
import ad.clinic.model.PatientData;
import ad.clinic.service.PatientService;
import ad.clinic.service.SurveyService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
@RequestMapping("/patient/survey")
public class SurveyController {

    private final PatientService patientService;


   private SurveyController(SurveyService surveyService, PatientService patientService) {
        this.surveyService = surveyService;
        this.patientService = patientService;
    }

    private final SurveyService surveyService;  

    @PostMapping("/note")
    public ResponseEntity<String> saveSurveyNote(@RequestBody PatientData note) {

        
     
        Patient patient =  patientService.findPatientById(note.getPatient().getId());
        
         patient.setPatientData(note);  
         note.setPatient(patient);
         surveyService.saveSurveyNote(note);
         patientService.savePatient(patient);
      
        return ResponseEntity.ok("Zapisanno poprawnie");
     }

    
    
}
