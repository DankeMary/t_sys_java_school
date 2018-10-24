package net.tsystems.bean;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("testTrainBean")
public class TestTrainBean {
    private Integer trainNumber;
    private List<StationTimeBean> stationsTiming;

    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<StationTimeBean> getStationsTiming() {
        return stationsTiming;
    }

    public void setStationsTiming(List<StationTimeBean> stationsTiming) {
        this.stationsTiming = stationsTiming;
    }
}
