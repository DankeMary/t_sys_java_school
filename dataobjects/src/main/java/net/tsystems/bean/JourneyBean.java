package net.tsystems.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class JourneyBean {
    private TripBean trip;
    //private int routeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date departureDay;
    //TODO seats left, canceleld, late

    public TripBean getTrip() {
        return trip;
    }

    public void setTrip(TripBean trip) {
        this.trip = trip;
    }



    /*public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }*/

    public Date getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(Date departureDay) {
        this.departureDay = departureDay;
    }
}
