import java.sql.Date;
import java.util.Collection;

public class TripDataDTO {
    private int id;
    private RouteDTO route;
    private int seatsLeft;
    private byte isCancelled;
    private byte isLate;
    private Date date;
    private Collection<TicketDTO> ticketsFrom;
    private Collection<TicketDTO> ticketsTo;

    public TripDataDTO(int id, RouteDTO route, int seatsLeft, byte isCancelled, byte isLate, Date date, Collection<TicketDTO> ticketsFrom, Collection<TicketDTO> ticketsTo) {
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

    public Collection<TicketDTO> getTicketsFrom() {
        return ticketsFrom;
    }

    public Collection<TicketDTO> getTicketsTo() {
        return ticketsTo;
    }
}
