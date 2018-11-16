package net.tsystems.bean;

public class TicketBean {
    private int id;
    private PassengerBean passenger;
    private TripDataBean from;
    private TripDataBean to;
    //private UserBean boughtBy;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PassengerBean getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerBean passenger) {
        this.passenger = passenger;
    }

    public TripDataBean getFrom() {
        return from;
    }

    public void setFrom(TripDataBean from) {
        this.from = from;
    }

    public TripDataBean getTo() {
        return to;
    }

    public void setTo(TripDataBean to) {
        this.to = to;
    }
}
