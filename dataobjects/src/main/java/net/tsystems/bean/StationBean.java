package net.tsystems.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("stationBean")
public class StationBean implements Serializable {
    private int id;
    private String name;

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
}
