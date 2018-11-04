package net.tsystems.bean;

import java.io.Serializable;
import java.util.List;

public class TrainPathBean implements Serializable {
    private int trainNumber;
    private List<StationBeanExpanded> stationsData;

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<StationBeanExpanded> getStationsData() {
        return stationsData;
    }

    public void setStationsData(List<StationBeanExpanded> stationsData) {
        this.stationsData = stationsData;
    }
}
