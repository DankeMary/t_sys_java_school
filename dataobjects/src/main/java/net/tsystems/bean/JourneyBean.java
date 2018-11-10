package net.tsystems.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class JourneyBean {
    private int journeyId;
    private TripBean trip;
    //private int routeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDay;
    //TODO seats left, canceleld, late


    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }

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

    public LocalDate getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(LocalDate departureDay) {
        this.departureDay = departureDay;
    }
}
