package net.tsystems.serviceobject;

public class TripSO {
    private int id;
    private TrainSO train;
    private StationSO from;
    private StationSO to;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
        return to.equals(tripSO.to);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + train.hashCode();
        result = 31 * result;
        result = 31 * result;
        result = 31 * result;
        return result;
    }
}
