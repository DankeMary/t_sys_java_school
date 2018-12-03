package net.tsystems.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;


public class StationBeanExpanded  implements Serializable {
    private int routeId;
    /*@Min(value = 0)
    private int orderIndex;*/
    //@NotNull
    //@Valid
    @Size(max = 65, message = "Max length - 65")
    @Pattern(regexp = "^$|^[a-zA-Z][a-zA-Z \\-0-9]+$", message = "Station name has to have at least one letter and can have only latin letters, digits, spaces and hyphens")
    private String stationName;
    //@NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime arrTime;
    //@NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime depTime;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    /*public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }*/

    /*public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }*/

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
