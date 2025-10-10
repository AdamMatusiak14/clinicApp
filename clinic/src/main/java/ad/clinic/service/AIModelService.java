package ad.clinic.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

@Service
public class AIModelService {


    public String findDisease( String textEN)  {  
        try{

      ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\Admin\\Projects\\clinicApp\\ml-model\\predict_model.py");
        pb.redirectErrorStream(true); // łączenie stderr z stdout

Process process = pb.start();

try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
    writer.write(textEN);
    writer.flush();           // <--- bardzo ważne
    process.getOutputStream().close(); // sygnał EOF dla Pythona
}

// odczyt wyniku
BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
StringBuilder output = new StringBuilder();
String line;
while ((line = reader.readLine()) != null) {
    output.append(line).append("\n");
}
process.waitFor();
   return output.toString().trim(); // zwraca np. "Predicted disease: Common Cold"

        } 
        catch (Exception e) {
            e.printStackTrace();
            return "Error predicting disease";
    }
}
    
}
