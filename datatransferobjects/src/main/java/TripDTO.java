import java.util.Set;

public class TripDTO {
    private int id;
    private TrainDTO train;
    private StationDTO from;
    private StationDTO to;
    private Set<RouteDTO> route;

    public TripDTO(int id, TrainDTO train, StationDTO from, StationDTO to, Set<RouteDTO> route) {
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

    public Set<RouteDTO> getRoute() {
        return route;
    }
}
