package net.tsystems.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "station")
@SuppressWarnings("JpaAttributeTypeInspection")
public class StationDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false, length = 65)
    private String name;
    @OneToMany(mappedBy = "from"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Set<TripDO> fromTrips;
    @OneToMany(mappedBy = "to"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Set<TripDO> toTrips;

    public Set<RouteDO> getRouteCurrs() {
        return routeCurrs;
    }

    public void setRouteCurrs(Set<RouteDO> routeCurrs) {
        this.routeCurrs = routeCurrs;
    }

    public Set<RouteDO> getRouteNext() {
        return routeNext;
    }

    public void setRouteNext(Set<RouteDO> routeNext) {
        this.routeNext = routeNext;
    }

    @OneToMany(mappedBy = "station")
    private Set<RouteDO> routeCurrs;

    @OneToMany(mappedBy = "nextStation")
    private Set<RouteDO> routeNext;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<TripDO> getFromTrips() {
        return fromTrips;
    }
    public void setFromTrips(Set<TripDO> fromTrips) {
        this.fromTrips = fromTrips;
    }

    public Set<TripDO> getToTrips() {
        return toTrips;
    }
    public void setToTrips(Set<TripDO> toTrips) {
        this.toTrips = toTrips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationDO stationDO = (StationDO) o;

        if (id != stationDO.id) return false;
        if (!name.equals(stationDO.name)) return false;
        if (fromTrips != null ? !fromTrips.equals(stationDO.fromTrips) : stationDO.fromTrips != null) return false;
        return toTrips != null ? toTrips.equals(stationDO.toTrips) : stationDO.toTrips == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (fromTrips != null ? fromTrips.hashCode() : 0);
        result = 31 * result + (toTrips != null ? toTrips.hashCode() : 0);
        return result;
    }
}
