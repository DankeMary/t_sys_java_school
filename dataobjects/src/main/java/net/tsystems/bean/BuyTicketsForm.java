package net.tsystems.bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class BuyTicketsForm implements Serializable {
    private int fromJourneyId;
    private int toJourneyId;
    @NotNull
    @Valid
    private List<PassengerBean> passengers;

    public int getFromJourneyId() {
        return fromJourneyId;
    }

    public void setFromJourneyId(int fromJourneyId) {
        this.fromJourneyId = fromJourneyId;
    }

    public int getToJourneyId() {
        return toJourneyId;
    }

    public void setToJourneyId(int toJourneyId) {
        this.toJourneyId = toJourneyId;
    }

    public List<PassengerBean> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerBean> passengers) {
        this.passengers = passengers;
    }
}
