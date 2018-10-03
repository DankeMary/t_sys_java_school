import java.util.Collection;

public class TripSO {
    private int id;
    private TrainSO train;
    private StationSO from;
    private StationSO to;
    private Collection<RouteSO> route;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Collection<RouteSO> getRoute() {
        return route;
    }
    public void setRoute(Collection<RouteSO> route) {
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
    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripSO tripSO = (TripSO) o;

        if (id != tripSO.id) return false;

        return true;
    }
    //TODO
    @Override
    public int hashCode() {
        return id;
    }
}
