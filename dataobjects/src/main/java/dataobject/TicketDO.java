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
    @JoinColumn(name = "trip_data", nullable = false)
    private TripDataDO tripData;

    public PassengerDO getPassenger() {return passenger;    }
    public void setPassenger(PassengerDO passenger) {
        this.passenger = passenger;
    }

    public TripDataDO getTripData() {
        return tripData;
    }
    public void setTripData(TripDataDO tripData) {
        this.tripData = tripData;
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
