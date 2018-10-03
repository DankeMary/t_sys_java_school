package dataobject;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@SuppressWarnings("JpaAttributeTypeInspection")
public class TicketDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "passenger", nullable = false)
    private PassengerDO passenger;
    @ManyToOne
    @JoinColumn(name = "from", nullable = false)
    private TripDataDO from;
    @ManyToOne
    @JoinColumn(name = "to", nullable = false)
    private TripDataDO to;

    public PassengerDO getPassenger() {return passenger;    }
    public void setPassenger(PassengerDO passenger) {
        this.passenger = passenger;
    }

    public TripDataDO getFrom() {
        return from;
    }
    public void setFrom(TripDataDO from) {
        this.from = from;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketDO ticketDO = (TicketDO) o;

        if (id != ticketDO.id) return false;

        return true;
    }
    //TODO
    @Override
    public int hashCode() {
        return id;
    }
}
