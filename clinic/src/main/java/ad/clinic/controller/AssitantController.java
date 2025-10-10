package ad.clinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.Asisstant;
import ad.clinic.service.AssistantService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/assistant")
public class AssitantController {


    AssistantService assistantService;

    public AssitantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

  

    @PostMapping("/description")
    public ResponseEntity<String> getResponse(@RequestBody Asisstant assistant) { 
    
        System.out.println("Otrzymano opis: " + assistant.getDescription());

         String result =  assistantService.findDiease(assistant.getDescription());
        assistant.setResponse(result);
   
        return ResponseEntity.ok(assistant.getResponse() );
    }
    

    
}
