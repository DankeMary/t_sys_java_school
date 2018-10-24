package net.tsystems.bean;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component("stationDataBean")
public class StationDataBean {
    private String stationName;
    private Timestamp arrTime;
    private Timestamp depTime;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
