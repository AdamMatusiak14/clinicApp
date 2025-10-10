package ad.clinic.DTO;

public class Asisstant {

    String description;
    String response;

    public Asisstant() {
    }
    public Asisstant(String description, String response) {
        this.description = description;
        this.response = response;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    
    
}
