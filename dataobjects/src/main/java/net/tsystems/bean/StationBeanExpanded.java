package net.tsystems.bean;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;


public class StationBeanExpanded {
    private int routeId;
    //private String stationName;
    private StationBean station;
    private Timestamp arrTime;
    private Timestamp depTime;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }

    public Timestamp getArrTime() {
        return arrTime;
    }

    public void setArrTime(Timestamp arrTime) {
        this.arrTime = arrTime;
    }

    public Timestamp getDepTime() {
        return depTime;
    }

    public void setDepTime(Timestamp depTime) {
        this.depTime = depTime;
    }
}
