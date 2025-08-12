package ad.clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.PatientCardDTO;
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
    public ResponseEntity<Patient> verifyPatient(@RequestBody PatientDTO patientDTO) { 

       
        Patient patient = patientService.verifyPatient(patientDTO);
        
        if (patient != null) {
            return ResponseEntity.ok(patient); // Pacjent istnieje
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Pacjent nie istnieje
        }

    }


    @PostMapping("/find")
    public ResponseEntity<PatientDTO> findPatient(@RequestBody PatientDTO patientDTO){ 
          Patient patient = patientService.findPatient(patientDTO); // patientRepository.findByFirstNameAndLastName(firstName, lastName);

          PatientDTO DTOPatient = new PatientDTO();
          DTOPatient.setId(patient.getId());
          DTOPatient.setFirstName(patient.getFirstName());  
            DTOPatient.setLastName(patient.getLastName());

        //   PatientCardDTO patientCardData = new PatientCardDTO();
        //   patientCardData.setPatientId(patient.getId());
        //   patientCardData.setName(patient.getFirstName());  
        //   patientCardData.setSurname(patient.getLastName());
        //   patientCardData.setInfoPatient(patient.getInfoPatient());
            
            if (patient != null) {
            return ResponseEntity.ok(DTOPatient); // Pacjent istnieje
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Pacjent nie istnieje
        }
    }


@GetMapping("/card") 
public ResponseEntity<PatientCardDTO> getPatientCard(@RequestParam Long id) {
    System.out.println("Fetching patient card for ID: " + id);
    Patient patient = patientService.findPatientById(id);
    
    if (patient != null) {
        PatientCardDTO patientCardDTO = new PatientCardDTO();
        patientCardDTO.setPatientId(patient.getId());
        patientCardDTO.setName(patient.getFirstName());
        patientCardDTO.setSurname(patient.getLastName());
        patientCardDTO.setInfoPatient(patient.getInfoPatient());
        
        return ResponseEntity.ok(patientCardDTO);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }           
       
    
}
}


