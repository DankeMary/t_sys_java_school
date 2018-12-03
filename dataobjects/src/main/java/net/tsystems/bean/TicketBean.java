package net.tsystems.bean;

import java.io.Serializable;

public class TicketBean  implements Serializable {
    private int id;
    private PassengerBean passenger;
    private TripDataBean from;
    private TripDataBean to;
    private UserBean boughtBy;


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

    public UserBean getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(UserBean boughtBy) {
        this.boughtBy = boughtBy;
    }
}
