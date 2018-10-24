package net.tsystems.entities;

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
    @JoinColumn(name = "dep_station", nullable = false)
    private TripDataDO from;
    @ManyToOne
    @JoinColumn(name = "arr_station", nullable = false)
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

    public TripDataDO getTo() {
        return to;
    }
    public void setTo(TripDataDO to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketDO ticketDO = (TicketDO) o;

        if (id != ticketDO.id) return false;
        if (!passenger.equals(ticketDO.passenger)) return false;
        if (!from.equals(ticketDO.from)) return false;
        return to.equals(ticketDO.to);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + passenger.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}
