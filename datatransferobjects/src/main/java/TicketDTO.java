public class TicketDTO {
    private int id;
    private PassengerDTO passenger;
    private TripDataDTO from;
    private TripDataDTO to;

    public TicketDTO(int id, PassengerDTO passenger, TripDataDTO from, TripDataDTO to) {
        this.id = id;
        this.passenger = passenger;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public PassengerDTO getPassenger() {
        return passenger;
    }

    public TripDataDTO getFrom() {
        return from;
    }

    public TripDataDTO getTo() {
        return to;
    }
}
