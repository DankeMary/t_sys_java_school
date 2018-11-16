package net.tsystems.serviceobject;

public class TicketSO {
    private int id;
    private PassengerSO passenger;
    private TripDataSO from;
    private TripDataSO to;
    //private UserSO boughtBy;

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

    /*public UserSO getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(UserSO boughtBy) {
        this.boughtBy = boughtBy;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketSO ticketSO = (TicketSO) o;

        if (id != ticketSO.id) return false;
        if (!passenger.equals(ticketSO.passenger)) return false;
        if (!from.equals(ticketSO.from)) return false;
        return to.equals(ticketSO.to);
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
