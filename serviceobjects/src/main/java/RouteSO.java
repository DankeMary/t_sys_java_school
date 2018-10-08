import java.sql.Timestamp;
import java.util.Set;

public class RouteSO {
    private int id;
    private TripSO trip;
    private StationSO station;
    private StationSO nextStation;
    private Timestamp arrival;
    private Timestamp departure;
    private Set<TripDataSO> tripData;


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

    public Set<TripDataSO> getTripData() {
        return tripData;
    }
    public void setTripData(Set<TripDataSO> tripData) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteSO routeSO = (RouteSO) o;

        if (id != routeSO.id) return false;
        if (!trip.equals(routeSO.trip)) return false;
        if (!station.equals(routeSO.station)) return false;
        if (nextStation != null ? !nextStation.equals(routeSO.nextStation) : routeSO.nextStation != null) return false;
        if (arrival != null ? !arrival.equals(routeSO.arrival) : routeSO.arrival != null) return false;
        if (departure != null ? !departure.equals(routeSO.departure) : routeSO.departure != null) return false;
        return tripData != null ? tripData.equals(routeSO.tripData) : routeSO.tripData == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + trip.hashCode();
        result = 31 * result + station.hashCode();
        result = 31 * result + (nextStation != null ? nextStation.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (tripData != null ? tripData.hashCode() : 0);
        return result;
    }
}
