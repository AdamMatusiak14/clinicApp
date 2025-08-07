package ad.clinic.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.PrescriptionDTO;
import ad.clinic.model.Doctor;
import ad.clinic.model.Patient;
import ad.clinic.model.Prescription;
import ad.clinic.service.DoctorService;
import ad.clinic.service.PatientService;
import ad.clinic.service.PdfGeneratorService;
import ad.clinic.service.PrescriptionService;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/prescription") 
public class PrescriptionController {
    
    private final PrescriptionService prescriptionService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final PdfGeneratorService pdfGeneratorService;

    public PrescriptionController(PrescriptionService prescriptionService, PatientService patientService, DoctorService doctorService, PdfGeneratorService pdfGeneratorService) {
        this.prescriptionService = prescriptionService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping("/findById")
    public ResponseEntity <byte[]> findById(@RequestParam Long patientId) throws IOException {

        List<PrescriptionDTO> prescriptionsDTO = new ArrayList<>();
        List<Prescription> prescriptions =  prescriptionService.findByPatientId(patientId);
       

        Iterator <Prescription> iteratorPrescription = prescriptions.iterator();

        while(iteratorPrescription.hasNext()){
            Prescription prescription = iteratorPrescription.next();

            Patient patient = patientService.findPatientById(patientId);
           Long doctorId = prescription.getDoctor().getId();
           Doctor doctor = doctorService.getDoctorById(doctorId);

           String doctorFirstName = doctor.getFirstName();
           String doctorLastName = doctor.getLastName();
           String code = prescription.getCode();
           LocalDate issueDate = prescription.getIssueDate();
           String medicine = prescription.getMedicine();
           String patientFirstName = patient.getFirstName();
           String patientLastName = patient.getLastName();

              System.out.println("Prescriptions Doctor: " + doctorFirstName + " " + doctorLastName);
              System.out.println("Prescriptions Patient: " + patientFirstName + " " + patientLastName);

           prescriptionsDTO.add(new PrescriptionDTO(code, issueDate, patientFirstName, patientLastName, doctorFirstName, doctorLastName, medicine));

        }

     

      byte[] pdfBytes = pdfGeneratorService.generatePrescriptionPdf(prescriptionsDTO);

       
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", "prescriptions.pdf");

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        
    }
       

       // Pacjent
       // Lekarze, lekarze ktorzy wystawili recepty
        
       

    
}
