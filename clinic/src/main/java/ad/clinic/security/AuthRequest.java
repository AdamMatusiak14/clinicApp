package ad.clinic.security;

public class AuthRequest {
    private String email;
    private String password;  
    private Long id; 

    public AuthRequest() {}

    public AuthRequest(String email, String password, Long id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }   

    public String getemail() {
        return email;
    }
    public void setemail(String email) {
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