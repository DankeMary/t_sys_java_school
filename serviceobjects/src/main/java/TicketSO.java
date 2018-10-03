public class TicketSO {
    private int id;
    private PassengerSO passenger;
    private TripDataSO tripData;

    public PassengerSO getPassenger() {return passenger;    }
    public void setPassenger(PassengerSO passenger) {
        this.passenger = passenger;
    }

    public TripDataSO getTripData() {
        return tripData;
    }
    public void setTripData(TripDataSO tripData) {
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

        TicketSO ticketSO = (TicketSO) o;

        if (id != ticketSO.id) return false;

        return true;
    }

    //TODO
    @Override
    public int hashCode() {
        return id;
    }
}
