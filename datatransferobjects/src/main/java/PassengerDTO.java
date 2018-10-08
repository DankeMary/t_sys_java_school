import java.sql.Date;
import java.util.Set;

public class PassengerDTO {

    private int id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Set<TicketDTO> tickets;

    public PassengerDTO(int id, String firstName, String lastName, Date birthday, Set<TicketDTO> tickets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Set<TicketDTO> getTickets() {
        return tickets;
    }
}
