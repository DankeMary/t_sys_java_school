package net.tsystems.bean;

import java.time.LocalDate;

public class TripDataBean {
    private int id;
    private RouteBean route;
    private int seatsLeft;
    private boolean isCancelled;
    private boolean isLate;
    private LocalDate date;
    private LocalDate tripDeparture;

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

    public boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public boolean getIsLate() {
        return isLate;
    }

    public void setIsLate(boolean isLate) {
        this.isLate = isLate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getTripDeparture() {
        return tripDeparture;
    }

    public void setTripDeparture(LocalDate tripDeparture) {
        this.tripDeparture = tripDeparture;
    }
}
