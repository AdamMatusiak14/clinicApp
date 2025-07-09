package ad.clinic.controller;

import org.springframework.web.bind.annotation.RestController;

import ad.clinic.model.Doctor;
import ad.clinic.service.DoctorService;

import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<Doctor> getAllDoctors() {
        System.out.println("To jest metoda getAllDoctors w DoctorController");
        return doctorService.getAllDoctors();
    }
}
