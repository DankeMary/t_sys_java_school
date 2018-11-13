package net.tsystems.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_data")
@SuppressWarnings("JpaAttributeTypeInspection")
public class TripDataDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "departure_day_time", nullable = false)
    private LocalDateTime date;
    @Column(name = "trip_departure", nullable = false)
    private LocalDate tripDeparture;

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

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDate getTripDeparture() {
        return tripDeparture;
    }
    public void setTripDeparture(LocalDate tripDeparture) {
        this.tripDeparture = tripDeparture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripDataDO that = (TripDataDO) o;

        if (id != that.id) return false;
        if (seatsLeft != that.seatsLeft) return false;
        if (isCancelled != that.isCancelled) return false;
        if (isLate != that.isLate) return false;
        if (!route.equals(that.route)) return false;
        if (!tripDeparture.equals(that.tripDeparture)) return false;
        return (date != null ? !date.equals(that.date) : that.date != null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + route.hashCode();
        result = 31 * result + seatsLeft;
        result = 31 * result + (int) isCancelled;
        result = 31 * result + (int) isLate;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result;
        result = 31 * result;
        return result;
    }
}
