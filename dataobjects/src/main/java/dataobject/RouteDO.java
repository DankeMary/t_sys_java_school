package dataobject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "route")
@SuppressWarnings("JpaAttributeTypeInspection")
public class RouteDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "trip" /*db column name*/, nullable = false)
    private TripDO trip;
    @Column(name = "station", nullable = false)
    private StationDO station;
    @Column(name = "next_station")
    private StationDO nextStation;
    @Column(name = "arrival", nullable = false)
    private Timestamp arrival;
    @Column(name = "departure", nullable = false)
    private Timestamp departure;
    @OneToMany(mappedBy = "route" /*class field name*//*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Set<TripDataDO> tripData;


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

    public Set<TripDataDO> getTripData() {
        return tripData;
    }
    public void setTripData(Set<TripDataDO> tripData) {
        this.tripData = tripData;
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

        if (id != routeDO.id) return false;
        if (!trip.equals(routeDO.trip)) return false;
        if (!station.equals(routeDO.station)) return false;
        if (nextStation != null ? !nextStation.equals(routeDO.nextStation) : routeDO.nextStation != null) return false;
        if (!arrival.equals(routeDO.arrival)) return false;
        if (!departure.equals(routeDO.departure)) return false;
        return tripData.equals(routeDO.tripData);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + trip.hashCode();
        result = 31 * result + station.hashCode();
        result = 31 * result + (nextStation != null ? nextStation.hashCode() : 0);
        result = 31 * result + arrival.hashCode();
        result = 31 * result + departure.hashCode();
        result = 31 * result + tripData.hashCode();
        return result;
    }
}
