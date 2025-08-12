package ad.clinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ad.clinic.model.Visit;
import ad.clinic.repository.VisitRepository;

@Service
public class VisitService {
    


    private VisitRepository visitRepository;
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

   public List <Visit> findVisitByPatientId(Long patientId) {
       
        List <Visit> visitis =  visitRepository.findByPatientId(patientId);
        return visitis;
       
    }   
}
