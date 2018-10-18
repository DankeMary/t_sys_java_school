package net.tsystems.serviceobject;

import java.sql.Date;
import java.util.Set;

public class TripDataSO {
    private int id;
    private RouteSO route;
    private int seatsLeft;
    private byte isCancelled;
    private byte isLate;
    private Date date;
    private Set<TicketSO> ticketsFrom;
    private Set<TicketSO> ticketsTo;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }
    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public byte getIsCancelled() {
        return isCancelled;
    }
    public void setIsCancelled(byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    public RouteSO getRoute() {
        return route;
    }
    public void setRoute(RouteSO route) {
        this.route = route;
    }

    public byte getIsLate() {
        return isLate;
    }
    public void setIsLate(byte isLate) {
        this.isLate = isLate;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Set<TicketSO> getTicketsFrom() {
        return ticketsFrom;
    }
    public void setTicketsFrom(Set<TicketSO> ticketsFrom) {
        this.ticketsFrom = ticketsFrom;
    }

    public Set<TicketSO> getTicketsTo() {
        return ticketsTo;
    }
    public void setTicketsTo(Set<TicketSO> ticketsTo) {
        this.ticketsTo = ticketsTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripDataSO that = (TripDataSO) o;

        if (id != that.id) return false;
        if (seatsLeft != that.seatsLeft) return false;
        if (isCancelled != that.isCancelled) return false;
        if (isLate != that.isLate) return false;
        if (!route.equals(that.route)) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (ticketsFrom != null ? !ticketsFrom.equals(that.ticketsFrom) : that.ticketsFrom != null) return false;
        return ticketsTo != null ? ticketsTo.equals(that.ticketsTo) : that.ticketsTo == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + route.hashCode();
        result = 31 * result + seatsLeft;
        result = 31 * result + (int) isCancelled;
        result = 31 * result + (int) isLate;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (ticketsFrom != null ? ticketsFrom.hashCode() : 0);
        result = 31 * result + (ticketsTo != null ? ticketsTo.hashCode() : 0);
        return result;
    }
}
