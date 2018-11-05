package net.tsystems.bean;

import java.util.Date;

public class TripDataBean {
    private int id;
    private RouteBean route;
    private int seatsLeft;
    private byte isCancelled;
    private byte isLate;
    private Date date;
    private Date tripDeparture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public byte getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    public byte getIsLate() {
        return isLate;
    }

    public void setIsLate(byte isLate) {
        this.isLate = isLate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTripDeparture() {
        return tripDeparture;
    }

    public void setTripDeparture(Date tripDeparture) {
        this.tripDeparture = tripDeparture;
    }
}
