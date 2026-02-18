package ad.clinic.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;

import ad.clinic.DTO.VisitDTO;
import ad.clinic.DTO.VisitDTOGrafik;
import ad.clinic.DTO.VisitFrontDTO;
import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;
import ad.clinic.model.Visit;
import ad.clinic.service.DoctorService;
import ad.clinic.service.PatientService;
import ad.clinic.service.VisitService;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {


@Mock
private VisitService visitService;

@Mock
private PatientService patientService;

@Mock
private DoctorService doctorService;

@InjectMocks
private VisitController visitController;

private Visit visit1;
private List<Visit> visits;
private Patient patient1;
private Doctor doctor1;
private LocalDate date;

private VisitDTO visitDTO;
private VisitDTOGrafik visitDTOGrafik;
private VisitFrontDTO visitFrontDTO;


    @BeforeEach
        void setUp() {

            patient1 = new Patient();
            patient1.setId(1L);
            patient1.setEmail("john@example.com");
            patient1.setFirstName("John");
            patient1.setLastName("Doe");
            patient1.setPassword("password");
            patient1.setRole("PATIENT");
            patient1.setInfoPatient("No known allergies");
            
            doctor1 = new Doctor();
            doctor1.setId(1L);
            doctor1.setEmail("alice@example.com");
            doctor1.setFirstName("Alice");
            doctor1.setLastName("Smith");
            doctor1.setPassword("password");
            doctor1.setRole("DOCTOR");
            doctor1.setAge(30);
            doctor1.setSpecialist("Cardiology");
            doctor1.setExperience("5 years");
            doctor1.setPhotoPath("/images/alice.jpg");

            visit1 = new Visit();
            visit1.setId(1L);
            visit1.setTime(LocalTime.of(10, 0));
            visit1.setDate(LocalDate.of(2024, 7, 1));
            visit1.setInfo("Regular check-up");
            visit1.setPatient(patient1);
            visit1.setDoctor(doctor1);
           

            visits = new ArrayList<>();
            visits.add(visit1);
    

            doctor1.setVisits(visits);
        
            

            date = LocalDate.of(2024, 7, 1);
            
            visitDTO = new VisitDTO();
            visitDTO.setVisitId(1L);
            visitDTO.setDate(LocalDate.of(2024, 7, 1));
            visitDTO.setTime(LocalTime.of(10, 0));
            visitDTO.setInfo("Regular check-up");
            visitDTO.setDoctorName("Alice");
            visitDTO.setDoctorSurname("Smith");

            visitDTOGrafik = new VisitDTOGrafik();
            visitDTOGrafik.setPatientName("John");
            visitDTOGrafik.setPatientSurname("Doe");
            visitDTOGrafik.setDate("2024-07-01");
            visitDTOGrafik.setTime("10:00");
            
            visitFrontDTO = new VisitFrontDTO();
            visitFrontDTO.setFirstName("John");
            visitFrontDTO.setLastName("Doe");
            visitFrontDTO.setDoctor(1L);
            visitFrontDTO.setDate("2024-07-01");
            visitFrontDTO.setTime("10:00");

        }

        @Test
        void testfindVisitById_Succsess() {
 
            List<Visit> visits = new ArrayList<>();
            visits.add(visit1);
            when(visitService.findVisitByPatientId(1L)).thenReturn(visits);

        List<VisitDTO> result = visitController.findVisitById(1L).getBody();

        
       // assertEquals(visitDTO.getVisitId(), result.getVisitId());
        assertEquals(visitDTO.getDate(), result.get(0).getDate());
        assertEquals(visitDTO.getTime(), result.get(0).getTime());


        }

        @Test
        void testfindVisitById_Fail() {
 
            List<Visit> visits = new ArrayList<>();
            when(visitService.findVisitByPatientId(1L)).thenReturn(Collections.emptyList());

        List<VisitDTO> result = visitController.findVisitById(1L).getBody();

        assertEquals(Collections.emptyList(), result);
            
            
        }

        @Test
        void findVisitByDoctorId_Success() {
         
            when(visitService.findVisitByDoctorId(1L)).thenReturn(visits);
            when(patientService.findById(1L)).thenReturn(patient1);

            List<?> result = visitController.findVisitsByDoctorId(1L).getBody();

            assertEquals(1, result.size());
            assertEquals(visitDTOGrafik.getPatientName(), ((VisitDTOGrafik)result.get(0)).getPatientName());
            assertEquals(visitDTOGrafik.getPatientSurname(), ((VisitDTOGrafik)result.get(0)).getPatientSurname());
            assertEquals(visitDTOGrafik.getDate(), ((VisitDTOGrafik)result.get(0)).getDate());
            assertEquals(visitDTOGrafik.getTime(), ((VisitDTOGrafik)result.get(0)).getTime());

            
        }

        @Test
        void findVisitByDoctorId_Fail() {
         
            when(visitService.findVisitByDoctorId(1L)).thenReturn(Collections.emptyList());

            List<?> result = visitController.findVisitsByDoctorId(1L).getBody();

            assertEquals(Collections.emptyList(), result);
            
        }
           

        @Test
        void getAvailableTimes_FullList() {
            List<String> slots = new ArrayList<>();
            slots.add("08:00");
            slots.add("08:30");
            slots.add("09:00");

           when(visitService.getAvailableTimes(1L, date)).thenReturn(slots);

           ResponseEntity<List<String>> response = visitController.getAvailableTimes(1L, date);

              assertEquals(slots, response.getBody());

        }   

        @Test
        void getAvailableTimes_EmptyList() {
            List<String> slots = new ArrayList<>();

           when(visitService.getAvailableTimes(1L, date)).thenReturn(Collections.emptyList());

           ResponseEntity<List<String>> response = visitController.getAvailableTimes(1L, date);

              assertEquals(Collections.emptyList(), response.getBody());

        }

        @Test
        void createVisit_Success() {
         
            when(patientService.findPatientByfirstNameAndLastName("John", "Doe")).thenReturn(patient1);  
            when(doctorService.getDoctorById(1L)).thenReturn(doctor1);
            doNothing().when(visitService).saveVisit(patient1, doctor1, "2024-07-01", "10:00");

            ResponseEntity<String> response = visitController.createVisit(visitFrontDTO);

            assertEquals(200, response.getStatusCodeValue());
            verify(visitService).saveVisit(patient1, doctor1, "2024-07-01", "10:00");
        
        }

        @Test
        void createVisit_Fail() {
         
            when(patientService.findPatientByfirstNameAndLastName("John", "Doe")).thenReturn(null);  
            when(doctorService.getDoctorById(1L)).thenReturn(doctor1);
            doNothing().when(visitService).saveVisit(null, doctor1, "2024-07-01", "10:00");

            ResponseEntity<String> response = visitController.createVisit(visitFrontDTO);

            assertEquals(200, response.getStatusCodeValue());
            verify(visitService).saveVisit(null, doctor1, "2024-07-01", "10:00");
        
        }

    
}
