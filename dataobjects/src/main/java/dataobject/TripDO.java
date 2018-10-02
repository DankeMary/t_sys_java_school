package dataobject;

import javax.persistence.*;
import java.util.Collection;

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
    private Collection<RouteDO> route;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Collection<RouteDO> getRoute() {
        return route;
    }
    public void setRoute(Collection<RouteDO> route) {
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

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
