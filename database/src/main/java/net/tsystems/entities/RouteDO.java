package net.tsystems.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "route")
@SuppressWarnings("JpaAttributeTypeInspection")
public class RouteDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "trip", nullable = false)
    private TripDO trip;
    @ManyToOne
    @JoinColumn(name = "station")
    private StationDO station;
    @ManyToOne
    @JoinColumn(name = "next_station")
    private StationDO nextStation;
    @Column(name = "arrival", nullable = false)
    private Timestamp arrival;
    @Column(name = "departure", nullable = false)
    private Timestamp departure;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TripDO getTrip() {
        return trip;
    }
    public void setTrip(TripDO trip) {
        this.trip = trip;
    }

    public StationDO getStation() {
        return station;
    }
    public void setStation(StationDO station) {
        this.station = station;
    }

    public StationDO getNextStation() {
        return nextStation;
    }
    public void setNextStation(StationDO nextStation) {
        this.nextStation = nextStation;
    }

    @Basic
    @Column(name = "arrival", nullable = false)
    public Timestamp getArrival() {
        return arrival;
    }

    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    @Basic
    @Column(name = "departure", nullable = false)
    public Timestamp getDeparture() {
        return departure;
    }

    public void setDeparture(Timestamp departure) {
        this.departure = departure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteDO routeDO = (RouteDO) o;
        return id == routeDO.id &&
                Objects.equals(trip, routeDO.trip) &&
                Objects.equals(station, routeDO.station) &&
                Objects.equals(nextStation, routeDO.nextStation) &&
                Objects.equals(arrival, routeDO.arrival) &&
                Objects.equals(departure, routeDO.departure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trip, station, nextStation, arrival, departure);
    }
}
