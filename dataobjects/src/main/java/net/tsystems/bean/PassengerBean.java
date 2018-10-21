package net.tsystems.bean;

import net.tsystems.entities.TicketDO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Component("passengerBean")
public class PassengerBean implements Serializable{
    private int id;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    //TODO private Set<TicketBean> tickets;

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

    /*public Set<TicketBean> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketBean> tickets) {
        this.tickets = tickets;
    }*/
}
