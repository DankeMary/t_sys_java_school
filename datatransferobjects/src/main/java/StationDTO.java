import java.util.Collection;

public class StationDTO {
    private int id;
    private String name;
    private Collection<TripDTO> fromTrips;
    private Collection<TripDTO> toTrips;

    public StationDTO(int id, String name, Collection<TripDTO> fromTrips, Collection<TripDTO> toTrips) {
        this.id = id;
        this.name = name;
        this.fromTrips = fromTrips;
        this.toTrips = toTrips;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<TripDTO> getFromTrips() {
        return fromTrips;
    }

    public Collection<TripDTO> getToTrips() {
        return toTrips;
    }
}
