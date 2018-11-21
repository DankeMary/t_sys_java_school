package net.tsystems.bean;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class StationBeanExpanded {
    private int routeId;
    //private String stationName;
    @Min(value = 0)
    private int orderIndex;
    @NotNull
    private StationBean station;
    @NotNull
    private LocalTime arrTime;
    @NotNull
    private LocalTime depTime;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }

    public LocalTime getArrTime() {
        return arrTime;
    }

    public void setArrTime(LocalTime arrTime) {
        this.arrTime = arrTime;
    }

    public LocalTime getDepTime() {
        return depTime;
    }

    public void setDepTime(LocalTime depTime) {
        this.depTime = depTime;
    }
}
