package dataobject;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "passenger")
@SuppressWarnings("JpaAttributeTypeInspection")
public class PassengerDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "first_name", length = 45)
    private String firstName;
    @Column(name = "first_name", nullable = false, length = 45)
    private String lastName;
    @Column(name = "birthday")
    private Date birthday;
    @OneToMany(mappedBy = "passenger"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Collection<TicketDO> tickets;


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

    public Collection<TicketDO> getTickets() {
        return tickets;
    }
    public void setTickets(Collection<TicketDO> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassengerDO that = (PassengerDO) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}
