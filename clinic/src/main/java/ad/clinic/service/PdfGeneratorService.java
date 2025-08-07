package ad.clinic.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import ad.clinic.DTO.PrescriptionDTO;
import ad.clinic.model.Prescription;


@Service
public class PdfGeneratorService { 

    public byte[] generatePrescriptionPdf(List<PrescriptionDTO> prescriptionsDtos) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(baos);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    document.add(new Paragraph("Lista recept"));
    
    for (PrescriptionDTO p : prescriptionsDtos) {
        document.add(new Paragraph("Kod dostepu: " + p.getCode() + "  "+ "Data: " + p.getIssueDate().toString()));
        document.add(new Paragraph("Pacjent: " + p.getFirstNamePatient() + " " + p.getLastNamePatient()));
        document.add(new Paragraph("Wystawca: " + p.getFirstNameDoctor() + " " + p.getLastNameDoctor()));
        document.add(new Paragraph("Przepisane leki:"));
        document.add(new Paragraph(p.getMedicine()));
        document.add(new Paragraph("-----------")); // Do uzupełnienia
    }

    document.close();
    return baos.toByteArray();
}

    
}
