package ad.clinic.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.clinic.model.Visit;
import ad.clinic.repository.VisitRepository;


@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitService visitService;

    private Visit visit;

    @BeforeEach
    void setUp() {
        visit = new Visit();
        visit.setId(1L);
        visit.setDate(LocalDate.of(2024, 1, 1));
        visit.setTime(LocalTime.of(10, 0));
        visit.setInfo("Regular check-up"); 
        
    }

    @Test
    void testFindVisitByPatientId() {
        Long patientId = 1L;
        List<Visit> visits = Arrays.asList(visit);
        when(visitRepository.findByPatientId(patientId)).thenReturn(visits);

        List<Visit> result = visitService.findVisitByPatientId(patientId);

        assertEquals(visits, result);
        verify(visitRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void testFindVisitByDoctorId() {
        Long doctorId = 1L;
        List<Visit> visits = Arrays.asList(visit);
        when(visitRepository.findByDoctorId(doctorId)).thenReturn(visits);

        List<Visit> result = visitService.findVisitByDoctorId(doctorId);

        assertEquals(visits, result);
        verify(visitRepository, times(1)).findByDoctorId(doctorId);
    }
//////////////////////////////
    @Test
    void testGetAvailableTimes() {  
        Long doctorId = 1L;
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<Visit> visits = Arrays.asList(visit);
        when(visitRepository.findByDoctorIdAndDate(doctorId, date)).thenReturn(visits);

        List<String> result = visitService.getAvailableTimes(doctorId, date);

        List<String> expectedSlots = visitService.generateSlots();
        expectedSlots.remove("10:00"); // Remove the taken slot

        assertEquals(expectedSlots, result);
        verify(visitRepository, times(1)).findByDoctorIdAndDate(doctorId, date);
    }  
    
    @Test
    void testGenerateSlots() {  
        List<String> result = visitService.generateSlots();

        List<String> expectedSlots = Arrays.asList(
            "08:00", "08:30", "09:00", "09:30",
            "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30"
        );

        assertEquals(expectedSlots, result);
    }

    @Test
    void testSaveVisit() {
        // Given
        String dateStr = "2024-01-01";
        String timeStr = "10:00";
        ad.clinic.model.Patient patient = new ad.clinic.model.Patient();
        ad.clinic.model.Doctor doctor = new ad.clinic.model.Doctor();

        // When
        visitService.saveVisit(patient, doctor, dateStr, timeStr);

        // Then
        verify(visitRepository, times(1)).save(any(Visit.class));
    }   
}
