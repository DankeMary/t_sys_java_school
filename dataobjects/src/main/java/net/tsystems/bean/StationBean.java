package net.tsystems.bean;

import net.tsystems.serviceobject.TripSO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component("stationBean")
public class StationBean implements Serializable {
    private int id;
    private String name;
    //TODO private Set<TripBean> fromTrips;
    //TODO private Set<TripBean> toTrips;

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

    /*public Set<TripBean> getFromTrips() {
        return fromTrips;
    }
    public void setFromTrips(Set<TripBean> fromTrips) {
        this.fromTrips = fromTrips;
    }

    public Set<TripBean> getToTrips() {
        return toTrips;
    }
    public void setToTrips(Set<TripBean> toTrips) {
        this.toTrips = toTrips;
    }*/
}
