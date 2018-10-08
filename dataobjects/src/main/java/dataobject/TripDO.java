package dataobject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "trip")
@SuppressWarnings("JpaAttributeTypeInspection")
public class TripDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "id", nullable = false)
    @OneToOne(/*cascade = CascadeType.ALL,*/ mappedBy = "trip")
    private TrainDO train;
    @ManyToOne
    @JoinColumn(name = "from", nullable = false)
    private StationDO from;
    @ManyToOne
    @JoinColumn(name = "to", nullable = false)
    private StationDO to;
    @OneToMany(mappedBy = "trip" /*class field name*//*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Set<RouteDO> route;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Set<RouteDO> getRoute() {
        return route;
    }
    public void setRoute(Set<RouteDO> route) {
        this.route = route;
    }

    public TrainDO getTrain() {
        return train;
    }
    public void setTrain(TrainDO train) {
        this.train = train;
    }

    public StationDO getFrom() {
        return from;
    }
    public void setFrom(StationDO from) {
        this.from = from;
    }

    public StationDO getTo() {
        return to;
    }
    public void setTo(StationDO to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripDO tripDO = (TripDO) o;

        if (id != tripDO.id) return false;
        if (!train.equals(tripDO.train)) return false;
        if (!from.equals(tripDO.from)) return false;
        if (!to.equals(tripDO.to)) return false;
        return route != null ? route.equals(tripDO.route) : tripDO.route == null;
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
