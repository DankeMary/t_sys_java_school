package net.tsystems.bean;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TrainBean {
    private int id;

    @NotNull(message = "Train number is mandatory")
    @Min(value = 1, message = "Train number has to be positive")
    @Max(value = Integer.MAX_VALUE, message = "Max train number - " + Integer.MAX_VALUE)
    private Long number;

    @NotNull(message = "Train capacity is mandatory")
    @Min(value = 1, message = "Train capacity has to be positive")
    @Max(value = Integer.MAX_VALUE, message = "Max train capacity - " + Integer.MAX_VALUE)
    private Long capacity;

    private TripBean trip;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }
    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getCapacity() {
        return capacity;
    }
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public TripBean getTrip() {
        return trip;
    }

    public void setTrip(TripBean trip) {
        this.trip = trip;
    }
}
