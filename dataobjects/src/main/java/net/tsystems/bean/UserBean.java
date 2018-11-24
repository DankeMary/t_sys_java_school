package net.tsystems.bean;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserBean {
    private int id;
    @Size(min = 3, max = 45, message = "Min length - 3, Max length - 45")
    @NotEmpty(message = "Username required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only latin letters and numbers are allowed")
    private String username;
    @NotEmpty
    private String password;
    private String role;

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
}
