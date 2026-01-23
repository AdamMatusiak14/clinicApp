package ad.clinic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.patientDTO.PatientDoctorCardDTO;
import ad.clinic.DTO.PatientCardDTO;
import ad.clinic.DTO.PatientDTO;
import ad.clinic.model.Patient;
import ad.clinic.model.PatientData;
import ad.clinic.service.PatientService;
import ad.clinic.service.SurveyService;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
@RequestMapping("/api/patient") 
public class PatientController {

    PatientService patientService;
    SurveyService surveyService;
 

   public PatientController(PatientService patientService, SurveyService surveyService) {
        this.patientService = patientService;
        this.surveyService = surveyService;
      
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

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
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
        


        //   PatientCardDTO patientCardData = new PatientCardDTO();
        //   patientCardData.setPatientId(patient.getId());
        //   patientCardData.setName(patient.getFirstName());  
        //   patientCardData.setSurname(patient.getLastName());
        //   patientCardData.setInfoPatient(patient.getInfoPatient());
            
            if (patient != null) {
                
            PatientDTO DTOPatient = new PatientDTO();
            DTOPatient.setId(patient.getId());
            DTOPatient.setFirstName(patient.getFirstName());  
            DTOPatient.setLastName(patient.getLastName());
            return ResponseEntity.ok(DTOPatient); // Pacjent istnieje
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Pacjent nie istnieje
        }
    }


@GetMapping("/card") 
public ResponseEntity<PatientCardDTO> getPatientCard(@RequestParam Long id) {
    System.out.println("Fetching patient card for ID: " + id);
    Patient patient = patientService.findById(id);
    
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

    
@GetMapping("/current")
public ResponseEntity<PatientCardDTO> getCurrentPatientCard(@AuthenticationPrincipal UserDetails userDetails){
    System.out.println("Getting current patient from JWT: " + userDetails.getUsername());
  
    Patient patient = patientService.findPatientByEmail(userDetails.getUsername());
    
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

@GetMapping("/doctorCard")
public ResponseEntity<PatientDoctorCardDTO> getPatientCardForDoctor(@RequestParam("id") Long id) { 
    System.out.println("Fetching patient card for doctor, patient ID: " + id); // jest 
    Patient patient = patientService.findById(id);
    if(patient == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    System.out.println("Found patient: " + (patient != null ? patient.getFirstName() + " " + patient.getLastName() : "null"));
     PatientData survey = surveyService.getSurveyByID(patient.getPatientData().getId());
     System.out.println("Found survey data: " + (survey != null ? "exists" : "null"));
    
    
        PatientDoctorCardDTO patientDoctorCardDTO = new PatientDoctorCardDTO();
        
        patientDoctorCardDTO.setId(patient.getId());
        patientDoctorCardDTO.setFirstName(patient.getFirstName());
        patientDoctorCardDTO.setLastName(patient.getLastName());
        patientDoctorCardDTO.setInfoPatient(patient.getInfoPatient()); 
        patientDoctorCardDTO.setAge(survey.getAge());
        patientDoctorCardDTO.setSex(survey.getSex());
        patientDoctorCardDTO.setTakingMedication(survey.getTakingMedication());
        patientDoctorCardDTO.setPastIllnesses(survey.getPastIllnesses());
        patientDoctorCardDTO.setChronicDiseases(survey.getChronicDiseases());
        patientDoctorCardDTO.setVaccinations(survey.getVaccinations());
        patientDoctorCardDTO.setAllergies(survey.getAllergies());
        patientDoctorCardDTO.setFamilyHistory(survey.getFamilyHistory());
        patientDoctorCardDTO.setSmoking(survey.getSmoking());
        patientDoctorCardDTO.setAlcohol(survey.getAlcohol());

        System.out.println("Constructed PatientDoctorCardDTO: " + patientDoctorCardDTO.getFirstName() + " " + patientDoctorCardDTO.getLastName()+" "+ patientDoctorCardDTO.getAge());
    
      
        
        return ResponseEntity.ok(patientDoctorCardDTO);
              
       

    }





}




