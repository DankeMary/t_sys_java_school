import java.util.Collection;

public class StationSO {
    private int id;
    private String name;
    private Collection<TripSO> fromTrips;
    private Collection<TripSO> toTrips;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Collection<TripSO> getFromTrips() {
        return fromTrips;
    }
    public void setFromTrips(Collection<TripSO> fromTrips) {
        this.fromTrips = fromTrips;
    }

    public Collection<TripSO> getToTrips() {
        return toTrips;
    }
    public void setToTrips(Collection<TripSO> toTrips) {
        this.toTrips = toTrips;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationSO stationSO = (StationSO) o;

        if (id != stationSO.id) return false;
        if (name != null ? !name.equals(stationSO.name) : stationSO.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
