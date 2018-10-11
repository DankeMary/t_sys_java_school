package net.tsystems.entities;

import javax.persistence.*;

@Entity
@Table(name = "train")
public class TrainDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "number", nullable = false)
    private int number;
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "train")
    private TripDO trip;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TripDO getTrip() {
        return trip;
    }
    public void setTrip(TripDO trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainDO trainDO = (TrainDO) o;

        if (id != trainDO.id) return false;
        if (number != trainDO.number) return false;
        if (capacity != trainDO.capacity) return false;
        return trip != null ? trip.equals(trainDO.trip) : trainDO.trip == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + capacity;
        result = 31 * result + (trip != null ? trip.hashCode() : 0);
        return result;
    }
}
