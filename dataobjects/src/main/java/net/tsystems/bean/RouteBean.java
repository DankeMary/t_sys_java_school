package net.tsystems.bean;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component("routeBean")
public class RouteBean {
    private int id;
    private TripBean trip;
    private StationBean station;
    private StationBean nextStation;
    private Timestamp arrival;
    private Timestamp departure;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TripBean getTrip() {
        return trip;
    }
    public void setTrip(TripBean trip) {
        this.trip = trip;
    }

    public StationBean getStation() {
        return station;
    }
    public void setStation(StationBean station) {
        this.station = station;
    }

    public StationBean getNextStation() {
        return nextStation;
    }
    public void setNextStation(StationBean nextStation) {
        this.nextStation = nextStation;
    }

    public Timestamp getArrival() {
        return arrival;
    }
    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    public Timestamp getDeparture() {
        return departure;
    }
    public void setDeparture(Timestamp departure) {
        this.departure = departure;
    }
}
