package net.tsystems.bean;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class TrainBean  implements Serializable {
    private int id;

    @NotNull(message = "Train number is mandatory")
    @Min(value = 1, message = "Train number has to be positive")
    @Max(value = Integer.MAX_VALUE, message = "Max train number - " + Integer.MAX_VALUE)
    private Long number;

    @NotNull(message = "Train capacity is mandatory")
    @Min(value = 1, message = "Train capacity has to be positive")
    @Max(value = Integer.MAX_VALUE, message = "Max train capacity - " + Integer.MAX_VALUE)
    private Long capacity;

    @NotNull(message = "Ticket price is mandatory")
    @DecimalMin(value = "1.0", inclusive = true, message = "Ticket price has to be positive")
    @DecimalMax(value = "7000.0", inclusive = true, message = "Max ticket price - " + 7000)
    @Digits(integer = 4, fraction = 2, message = "Fraction has to consist of 2 digits")
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TripBean getTrip() {
        return trip;
    }

    public void setTrip(TripBean trip) {
        this.trip = trip;
    }
}
