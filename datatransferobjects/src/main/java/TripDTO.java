import java.util.Collection;

public class TripDTO {
    private int id;
    private TrainDTO train;
    private StationDTO from;
    private StationDTO to;
    private Collection<RouteDTO> route;

    public TripDTO(int id, TrainDTO train, StationDTO from, StationDTO to, Collection<RouteDTO> route) {
        this.id = id;
        this.train = train;
        this.from = from;
        this.to = to;
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public TrainDTO getTrain() {
        return train;
    }

    public StationDTO getFrom() {
        return from;
    }

    public StationDTO getTo() {
        return to;
    }

    public Collection<RouteDTO> getRoute() {
        return route;
    }
}
