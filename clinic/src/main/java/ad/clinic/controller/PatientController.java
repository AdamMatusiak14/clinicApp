package ad.clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.PatientDTO;
import ad.clinic.model.Patient;
import ad.clinic.service.PatientService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
@RequestMapping("/patient")
public class PatientController {

    PatientService patientService;

   public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerPatient(@RequestBody Patient patient) {

        try {
            patientService.registerPatient(patient);
            return ResponseEntity.ok("Patient registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering patient: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPatient(@RequestBody PatientDTO patientDTO) { 

       
        boolean exists = patientService.verifyPatient(patientDTO);
         return ResponseEntity.ok(exists);
     

      
    
}
}