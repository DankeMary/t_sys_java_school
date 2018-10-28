package net.tsystems.entities;

import javax.persistence.*;

@Entity
@Table(name = "trip")
@SuppressWarnings("JpaAttributeTypeInspection")
public class TripDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne
    @JoinColumn(name = "train")
    private TrainDO train;
    @ManyToOne
    @JoinColumn(name = "station_from", nullable = false)
    private StationDO from;
    @ManyToOne
    @JoinColumn(name = "station_to", nullable = false)
    private StationDO to;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
        return (to.equals(tripDO.to));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result;
        result = 31 * result;
        result = 31 * result;
        result = 31 * result;
        return result;
    }
}
