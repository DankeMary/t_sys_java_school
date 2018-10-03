import java.sql.Timestamp;
import java.util.Collection;

public class RouteSO {
    private int id;
    private TripSO trip;
    private StationSO station;
    private StationSO nextStation;
    private Timestamp arrival;
    private Timestamp departure;
    private Collection<TripDataSO> tripData;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TripSO getTrip() {
        return trip;
    }
    public void setTrip(TripSO trip) {
        this.trip = trip;
    }

    public StationSO getStation() {
        return station;
    }
    public void setStation(StationSO station) {
        this.station = station;
    }

    public StationSO getNextStation() {
        return nextStation;
    }
    public void setNextStation(StationSO nextStation) {
        this.nextStation = nextStation;
    }

    public Collection<TripDataSO> getTripData() {
        return tripData;
    }
    public void setTripData(Collection<TripDataSO> tripData) {
        this.tripData = tripData;
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

    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteSO routeDO = (RouteSO) o;

        if (id != routeDO.id) return false;
        if (nextStation != null ? !nextStation.equals(routeDO.nextStation) : routeDO.nextStation != null) return false;
        if (arrival != null ? !arrival.equals(routeDO.arrival) : routeDO.arrival != null) return false;
        if (departure != null ? !departure.equals(routeDO.departure) : routeDO.departure != null) return false;

        return true;
    }
    //TODO
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nextStation != null ? nextStation.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        return result;
    }
}
