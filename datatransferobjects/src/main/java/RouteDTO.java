import java.sql.Timestamp;
import java.util.Collection;

public class RouteDTO {
    private int id;
    private TripDTO trip;
    private StationDTO station;
    private StationDTO nextStation;
    private Timestamp arrival;
    private Timestamp departure;
    private Collection<TripDataDTO> tripData;

    public RouteDTO(int id, TripDTO trip, StationDTO station, StationDTO nextStation, Timestamp arrival, Timestamp departure, Collection<TripDataDTO> tripData) {
        this.id = id;
        this.trip = trip;
        this.station = station;
        this.nextStation = nextStation;
        this.arrival = arrival;
        this.departure = departure;
        this.tripData = tripData;
    }

    public int getId() {
        return id;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public StationDTO getStation() {
        return station;
    }

    public StationDTO getNextStation() {
        return nextStation;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public Timestamp getDeparture() {
        return departure;
    }

    public Collection<TripDataDTO> getTripData() {
        return tripData;
    }
}
