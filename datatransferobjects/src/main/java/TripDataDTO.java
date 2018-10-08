import java.sql.Date;
import java.util.Set;

public class TripDataDTO {
    private int id;
    private RouteDTO route;
    private int seatsLeft;
    private byte isCancelled;
    private byte isLate;
    private Date date;
    private Set<TicketDTO> ticketsFrom;
    private Set<TicketDTO> ticketsTo;

    public TripDataDTO(int id, RouteDTO route, int seatsLeft, byte isCancelled, byte isLate, Date date, Set<TicketDTO> ticketsFrom, Set<TicketDTO> ticketsTo) {
        this.id = id;
        this.route = route;
        this.seatsLeft = seatsLeft;
        this.isCancelled = isCancelled;
        this.isLate = isLate;
        this.date = date;
        this.ticketsFrom = ticketsFrom;
        this.ticketsTo = ticketsTo;
    }

    public int getId() {
        return id;
    }

    public RouteDTO getRoute() {
        return route;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public byte getIsCancelled() {
        return isCancelled;
    }

    public byte getIsLate() {
        return isLate;
    }

    public Date getDate() {
        return date;
    }

    public Set<TicketDTO> getTicketsFrom() {
        return ticketsFrom;
    }

    public Set<TicketDTO> getTicketsTo() {
        return ticketsTo;
    }
}
