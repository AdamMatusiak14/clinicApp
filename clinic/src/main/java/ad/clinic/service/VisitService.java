package ad.clinic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;
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
    
    public List<Visit> findVisitByDoctorId(Long doctorId) {
        return visitRepository.findByDoctorId(doctorId);
    } 
    
    public List<String> getAvailableTimes(Long doctorId, LocalDate date) {
        // Logic to find available times for the given doctor on the specified date
        List<String> allSlots = generateSlots();
        List<String> takenSlots = visitRepository.findByDoctorIdAndDate(doctorId, date)
            .stream()
            .map(v-> v.getTime().toString())
            .collect(java.util.stream.Collectors.toList());
            
            allSlots.removeAll(takenSlots);
        return allSlots;
    }

    private List<String> generateSlots() {
        // Generate all possible time slots (e.g., every 30 minutes from 08:00 to 15:30)
        List<String> slots = new java.util.ArrayList<>();
        for (int hour = 8; hour < 16; hour++) {
            slots.add(String.format("%02d:00", hour));
            slots.add(String.format("%02d:30", hour));
        }
        return slots;
    }  
    
    
public void saveVisit(Patient patient, Doctor doctor, String dateStr, String timeStr) {
    Visit visit = new Visit();
    visit.setDate(LocalDate.parse(dateStr));
    visit.setTime(java.time.LocalTime.parse(timeStr));
    visit.setPatient(patient); 
    visit.setDoctor(doctor);
    visitRepository.save(visit);
    }

}
