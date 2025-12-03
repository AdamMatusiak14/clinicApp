package ad.clinic.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.model.Patient;
import ad.clinic.model.PatientData;
import ad.clinic.service.PatientService;
import ad.clinic.service.SurveyService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
@RequestMapping("/api/patient/survey")
public class SurveyController {

    private final PatientService patientService;


   public SurveyController(SurveyService surveyService, PatientService patientService) {
        this.surveyService = surveyService;
        this.patientService = patientService;
    }

    private final SurveyService surveyService;  

    @PutMapping("/note")
    public ResponseEntity<String> saveSurveyNote(@RequestBody PatientData note, Principal principal) {

      String username = principal.getName();  
      System.out.println("Authenticated patient username: " + username);
      Patient patient = patientService.findByUsername(username);  
      PatientData patientData = patient.getPatientData();   
      
      
      if (patientData == null) { // Jest NULL
        patient.setPatientData(note);
        surveyService.saveSurveyNote(note);
        patientService.savePatient(patient);


      } else { // NOT NULL

          patientData.setAge(note.getAge());
          patientData.setSex(note.getSex());  
          patientData.setTakingMedication(note.getTakingMedication());
          patientData.setPastIllnesses(note.getPastIllnesses());
          patientData.setChronicDiseases(note.getChronicDiseases());
          patientData.setVaccinations(note.getVaccinations());
          patientData.setAllergies(note.getAllergies());
          patientData.setFamilyHistory(note.getFamilyHistory());
          patientData.setSmoking(note.getSmoking());
          patientData.setAlcohol(note.getAlcohol());   
          patient.setPatientData(patientData);

          surveyService.saveSurveyNote(patientData);
          patientService.savePatient(patient);


      }

 
    
        //  patient.setPatientData(note);  
        //  recordSurvey.setPatient(patient);
        //  recordSurvey.setAge(note.getAge());
        //  recordSurvey.setSex(note.getSex());
        //  recordSurvey.setTakingMedication(note.getTakingMedication());
        //  recordSurvey.setPastIllnesses(note.getPastIllnesses());
        //  recordSurvey.setChronicDiseases(note.getChronicDiseases());
        //  recordSurvey.setVaccinations(note.getVaccinations());
        //  recordSurvey.setAllergies(note.getAllergies());
        //  recordSurvey.setFamilyHistory(note.getFamilyHistory());
        //  recordSurvey.setSmoking(note.getSmoking());
        //  recordSurvey.setAlcohol(note.getAlcohol());   
       



         //surveyService.saveSurveyNote(recordSurvey);
        // patientService.savePatient(patient);
      
        return ResponseEntity.ok("Zapisanno poprawnie");
     }

    
    
}
