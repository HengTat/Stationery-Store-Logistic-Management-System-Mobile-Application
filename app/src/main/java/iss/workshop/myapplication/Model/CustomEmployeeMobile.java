package iss.workshop.myapplication.Model;

public class CustomEmployeeMobile  {
    private int id;
    private String email;
    private String name;
    private String password;
    private String role;
    public int getId(){ return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){ this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){ this.password = password; }
    public String getRole() {
        return role;
    }
    public void setRole(String role) { this.role = role; }
}
