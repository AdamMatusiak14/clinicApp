 package ad.clinic.controller;

import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.DoctorDTO;
import ad.clinic.DTO.DoctorNameDTO;
import ad.clinic.model.Doctor;
import ad.clinic.service.DoctorService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;



@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api") 
public class DoctorController {
    



    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        System.out.println("To jest metoda getAllDoctors w DoctorController");
        
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
      
}
