package net.tsystems.bean;


public class UserBean {
    private int id;
    private String username;
    private String password;
    private String role;
    private PassengerBean passenger;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public PassengerBean getPassenger() {
        return passenger;
    }
    public void setPassenger(PassengerBean passenger) {
        this.passenger = passenger;
    }
}
