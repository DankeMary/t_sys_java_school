package net.tsystems.bean;

import javax.validation.Valid;
import java.io.Serializable;

public class BuyTicketForm implements Serializable {
    @Valid
    private PassengerBean passenger;
    private int fromJourneyId;
    private int toJourneyId;

    public PassengerBean getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerBean passenger) {
        this.passenger = passenger;
    }

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
}
