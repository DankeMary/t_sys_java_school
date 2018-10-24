package net.tsystems.bean;

public class TripBean {
    private int id;
    private TrainBean train;
    private StationBean from;
    private StationBean to;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TrainBean getTrain() {
        return train;
    }
    public void setTrain(TrainBean train) {
        this.train = train;
    }

    public StationBean getFrom() {
        return from;
    }
    public void setFrom(StationBean from) {
        this.from = from;
    }

    public StationBean getTo() {
        return to;
    }
    public void setTo(StationBean to) {
        this.to = to;
    }
}
