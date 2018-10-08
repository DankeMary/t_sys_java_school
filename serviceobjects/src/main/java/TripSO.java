import java.util.Set;

public class TripSO {
    private int id;
    private TrainSO train;
    private StationSO from;
    private StationSO to;
    private Set<RouteSO> route;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Set<RouteSO> getRoute() {
        return route;
    }
    public void setRoute(Set<RouteSO> route) {
        this.route = route;
    }

    public TrainSO getTrain() {
        return train;
    }
    public void setTrain(TrainSO train) {
        this.train = train;
    }

    public StationSO getFrom() {
        return from;
    }
    public void setFrom(StationSO from) {
        this.from = from;
    }

    public StationSO getTo() {
        return to;
    }
    public void setTo(StationSO to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripSO tripSO = (TripSO) o;

        if (id != tripSO.id) return false;
        if (!train.equals(tripSO.train)) return false;
        if (!from.equals(tripSO.from)) return false;
        if (!to.equals(tripSO.to)) return false;
        return route != null ? route.equals(tripSO.route) : tripSO.route == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + train.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + (route != null ? route.hashCode() : 0);
        return result;
    }
}
