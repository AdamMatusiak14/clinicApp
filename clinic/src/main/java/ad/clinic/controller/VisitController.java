package ad.clinic.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.VisitDTO;
import ad.clinic.DTO.VisitFrontDTO;
import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;
import ad.clinic.model.Visit;
import ad.clinic.service.DoctorService;
import ad.clinic.service.PatientService;
import ad.clinic.service.VisitService;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
@RequestMapping("api/visit")
public class VisitController {

    private VisitService visitService;
    private PatientService patientService;
    private DoctorService doctorService;

    public VisitController(VisitService visitService,  PatientService patientService, DoctorService doctorService) {
        this.visitService = visitService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    
    @PostMapping("/findById")
    ResponseEntity <List<VisitDTO>> findVisitById(@RequestParam Long patientId) { 

        System.out.println("Jestem VisitController");

        List <VisitDTO> visitDTO = new ArrayList<>();
        List <Visit> visits = visitService.findVisitByPatientId(patientId);

        Iterator<Visit> iteratorVisit = visits.iterator();

        while(iteratorVisit.hasNext()) {
            Visit visit = iteratorVisit.next();
            VisitDTO visitDto = new VisitDTO(
                visit.getId(),
                visit.getDate(),
                visit.getTime(),
                visit.getInfo(),
                visit.getDoctor().getFirstName(),
                visit.getDoctor().getLastName()
            );
            visitDTO.add(visitDto);
        }


        
        return ResponseEntity.ok(visitDTO); // Return the found visit DTO
    }


    @GetMapping("/available")
    ResponseEntity<List<String>> getAvailableTimes(@RequestParam Long doctorId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<String> availableTimes = visitService.getAvailableTimes(doctorId, date);
        System.out.println("Tu kontroler Available");
        return ResponseEntity.ok(availableTimes); 
    }

    @PostMapping("/create")
    ResponseEntity<String> createVisit(@RequestBody VisitFrontDTO visitFrontDTO) {

        System.out.println("visiitFrontDTO firstName: " + visitFrontDTO.getFirstName());
        System.out.println("visiitFrontDTO lastName: " + visitFrontDTO.getLastName());

        System.out.println("visiitFrontDTO DocID: " + visitFrontDTO.getDoctor());
        System.out.println("visiitFrontDTO date: " + visitFrontDTO.getDate());
          System.out.println("visiitFrontDTO time: " + visitFrontDTO.getTime());

        String firstName = visitFrontDTO.getFirstName();
        String lastName = visitFrontDTO.getLastName();
        Patient patient = patientService.findPatientByfirstNameAndLastName(firstName, lastName);

        Doctor doctor = doctorService.getDoctorById(visitFrontDTO.getDoctor());
        

        visitService.saveVisit( patient,doctor, visitFrontDTO.getDate(), visitFrontDTO.getTime());
        
        return ResponseEntity.ok("Visit created successfully"); // Return success message
    }


    
}
