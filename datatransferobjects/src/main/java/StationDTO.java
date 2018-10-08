import java.util.Set;

public class StationDTO {
    private int id;
    private String name;
    private Set<TripDTO> fromTrips;
    private Set<TripDTO> toTrips;

    public StationDTO(int id, String name, Set<TripDTO> fromTrips, Set<TripDTO> toTrips) {
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

    public Set<TripDTO> getFromTrips() {
        return fromTrips;
    }

    public Set<TripDTO> getToTrips() {
        return toTrips;
    }
}
