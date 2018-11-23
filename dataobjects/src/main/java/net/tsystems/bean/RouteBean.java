package net.tsystems.bean;


import java.time.LocalTime;


public class RouteBean {
    private int id;
    private TripBean trip;
    private StationBean station;
    private StationBean nextStation;
    private LocalTime arrival;
    private LocalTime departure;

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

    public LocalTime getArrival() {
        return arrival;
    }
    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public LocalTime getDeparture() {
        return departure;
    }
    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }
}
