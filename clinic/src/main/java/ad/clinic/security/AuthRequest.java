package ad.clinic.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message="Password must not be empty")
    private String password;  

    private Long id; 

    public AuthRequest() {}

    public AuthRequest(String email, String password, Long id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }   

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

        


}