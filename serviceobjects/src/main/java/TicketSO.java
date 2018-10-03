public class TicketSO {
    private int id;
    private PassengerSO passenger;
    private TripDataSO from;
    private TripDataSO to;

    public PassengerSO getPassenger() {return passenger;    }
    public void setPassenger(PassengerSO passenger) {
        this.passenger = passenger;
    }

    public TripDataSO getFrom() {
        return from;
    }
    public void setFrom(TripDataSO from) {
        this.from = from;
    }

    public TripDataSO getTo() {
        return to;
    }
    public void setTo(TripDataSO to) {
        this.to = to;
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
