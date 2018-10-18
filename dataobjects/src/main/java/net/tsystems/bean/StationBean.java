package net.tsystems.bean;

import net.tsystems.serviceobject.TripSO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component("stationBean")
public class StationBean implements Serializable {
    private int id;
    private String name;
    private Set<TripSO> fromTrips;
    private Set<TripSO> toTrips;

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

    public Set<TripSO> getFromTrips() {
        return fromTrips;
    }
    public void setFromTrips(Set<TripSO> fromTrips) {
        this.fromTrips = fromTrips;
    }

    public Set<TripSO> getToTrips() {
        return toTrips;
    }
    public void setToTrips(Set<TripSO> toTrips) {
        this.toTrips = toTrips;
    }
}
