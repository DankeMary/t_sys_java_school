package net.tsystems.bean;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


public class PassengerBean implements Serializable{
    private int id;

    @Size(max = 45, message = "Max length - 45")
    @Pattern(regexp = "^[a-zA-Z \\-]+$", message = "Only latin letters, spaces and hyphens are allowed")
    private String firstName;

    @NotEmpty(message = "Last name required")
    @Size(min = 3, max = 45, message = "Min length - 3, Max length - 45")
    @Pattern(regexp = "^[a-zA-Z \\-]+$", message = "Only latin letters, spaces and hyphens are allowed")
    private String lastName;

    @NotNull(message = "Birthday cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
