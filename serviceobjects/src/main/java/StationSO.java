import java.util.Set;

public class StationSO {
    private int id;
    private String name;
    private Set<TripSO> fromTrips;
    private Set<TripSO> toTrips;

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

    public Set<TripSO> getFromTrips() {
        return fromTrips;
    }
    public void setFromTrips(Set<TripSO> fromTrips) {
        this.fromTrips = fromTrips;
    }

    public Set<TripSO> getToTrips() {
        return toTrips;
    }
    public void setToTrips(Set<TripSO> toTrips) {
        this.toTrips = toTrips;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationSO stationSO = (StationSO) o;

        if (id != stationSO.id) return false;
        if (!name.equals(stationSO.name)) return false;
        if (fromTrips != null ? !fromTrips.equals(stationSO.fromTrips) : stationSO.fromTrips != null) return false;
        return toTrips != null ? toTrips.equals(stationSO.toTrips) : stationSO.toTrips == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (fromTrips != null ? fromTrips.hashCode() : 0);
        result = 31 * result + (toTrips != null ? toTrips.hashCode() : 0);
        return result;
    }
}
