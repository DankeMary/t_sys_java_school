package net.tsystems.bean;

public class TripBeanObjectless {
    private Integer id;
    private Integer train;
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrain() {
        return train;
    }
    public void setTrain(Integer train) {
        this.train = train;
    }
}
