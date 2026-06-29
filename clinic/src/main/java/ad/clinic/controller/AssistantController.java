package ad.clinic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.clinic.DTO.Assistant;
import ad.clinic.service.AssistantService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/assistant")
public class AssistantController {

    private static final Logger logger = LoggerFactory.getLogger(AssistantController.class);

    AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

  

    @PostMapping("/description")
    public ResponseEntity<String> getResponse(@RequestBody Assistant assistant) { 
    
        logger.debug("Received assistant description: {}", assistant.getDescription());

         String result =  assistantService.findDiease(assistant.getDescription());
        assistant.setResponse(result);
   
        return ResponseEntity.ok(assistant.getResponse() );
    }
    

    
}
