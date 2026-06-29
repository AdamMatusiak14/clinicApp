 package ad.clinic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.DoctorDTO;
import ad.clinic.DTO.DoctorNameDTO;
import ad.clinic.model.Doctor;
import ad.clinic.service.DoctorService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;



@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api") 
public class DoctorController {
    
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        logger.debug("Retrieving all doctors");
        
        List<Doctor> doctors =  doctorService.getAllDoctors();

        List<DoctorDTO> doctorsDTO = doctors.stream().map(doctor -> new DoctorDTO(
            doctor.getFirstName(),
            doctor.getLastName(),
            doctor.getPassword(),
            doctor.getRole(),
            doctor.getAge(),
            doctor.getSpecialist(),
            doctor.getExperience(),
            doctor.getPhotoPath()
        )).toList();

        return ResponseEntity.ok(doctorsDTO);
    }


    @GetMapping("/doctors/names")
    ResponseEntity <List<DoctorNameDTO>> getAllDoctorNames() {

    List<Doctor> doctors = doctorService.getAllDoctors();

    List<DoctorNameDTO> doctorNamesDTO = doctors.stream().map(doctor -> new DoctorNameDTO(
        doctor.getId(),
        doctor.getFirstName(),
        doctor.getLastName()
    )).toList();

    return ResponseEntity.ok(doctorNamesDTO);
    }
      

    @GetMapping("/doctor/panel")
   // @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getDoctorPanel(Principal principal) {
            String username = principal.getName();
            logger.debug("Authenticated doctor username: {}", username);
        return ResponseEntity.ok("Welcome to the Doctor's Panel!");
    }
}
