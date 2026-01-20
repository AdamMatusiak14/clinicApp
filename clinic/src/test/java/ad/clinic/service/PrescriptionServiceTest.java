package ad.clinic.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.clinic.repository.PrescriptionRepository;


import ad.clinic.model.Prescription;


@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository; 

    @InjectMocks
    private PrescriptionService prescriptionService;
    
    private Prescription prescription;

    
   @BeforeEach
    void setUp() {
        
        prescription = new Prescription();
        prescription.setId(1L);
        prescription.setCode("RX12345");
        prescription.setMedicine("Amoxicillin 500mg");
    }  
    
    @Test
    void testFindByPatientId() {
        Long patientId = 1L;
        List<Prescription> prescriptions = Arrays.asList(prescription);
        when(prescriptionRepository.findByPatientId(patientId)).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.findByPatientId(patientId);

        assertEquals(prescriptions, result);
        verify(prescriptionRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void testSavePrescription() {
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);
        prescriptionService.savePrescription(prescription);

        verify(prescriptionRepository, times(1)).save(prescription);
    }


    
}
