package net.tsystems.bean;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;


@Component("passengerBean")
@ManagedBean(name = "passengerBean")
public class PassengerBean implements Serializable{

    private String firstName;
    private String lastName;
    private Date birthday;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
