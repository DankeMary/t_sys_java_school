package dataobject;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "trip_data")
@SuppressWarnings("JpaAttributeTypeInspection")
public class TripDataDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "route", nullable = false)
    private RouteDO route;
    @Column(name = "seats_left", nullable = false)
    private int seatsLeft;
    @Column(name = "is_cancelled", nullable = false)
    private byte isCancelled;
    @Column(name = "is_late", nullable = false)
    private byte isLate;
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "from"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Collection<TicketDO> ticketsFrom;


    @OneToMany(mappedBy = "to"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Collection<TicketDO> ticketsTo;


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

    public RouteDO getRoute() {
        return route;
    }
    public void setRoute(RouteDO route) {
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

    public Collection<TicketDO> getTicketsFrom() {
        return ticketsFrom;
    }
    public void setTicketsFrom(Collection<TicketDO> tickets) {
        this.ticketsFrom = tickets;
    }

    public Collection<TicketDO> getTicketsTo() {
        return ticketsTo;
    }
    public void setTicketsTo(Collection<TicketDO> ticketsTo) {
        this.ticketsTo = ticketsTo;
    }

    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripDataDO that = (TripDataDO) o;

        if (id != that.id) return false;
        if (seatsLeft != that.seatsLeft) return false;
        if (isCancelled != that.isCancelled) return false;
        if (route != that.route) return false;
        if (isLate != that.isLate) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }
    //TODO
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + seatsLeft;
        result = 31 * result + (int) isCancelled;
        //result = 31 * result + route;
        result = 31 * result + (int) isLate;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
