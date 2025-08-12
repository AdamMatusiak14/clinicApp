package ad.clinic.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.VisitDTO;
import ad.clinic.model.Visit;
import ad.clinic.service.VisitService;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
@RequestMapping("/visit")
public class VisitController {

    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    
    @PostMapping("/findById")
    ResponseEntity <List<VisitDTO>> findVisitById(@RequestParam Long patientId) { // Stwórz tabelę Visits

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


        // Logic to find visit by ID
        // This is a placeholder, actual implementation will depend on VisitService methods
        return ResponseEntity.ok(visitDTO); // Return the found visit DTO
    }
    
}
