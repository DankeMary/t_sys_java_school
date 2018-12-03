package net.tsystems.bean;


import java.io.Serializable;

public class ScheduleBean  implements Serializable {
    private TrainBean train;
    private TripBean trip;
    private RouteBean route;
    private TripDataBean tripData;

    public TrainBean getTrain() {
        return train;
    }

    public void setTrain(TrainBean train) {
        this.train = train;
    }

    public TripBean getTrip() {
        return trip;
    }

    public void setTrip(TripBean trip) {
        this.trip = trip;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public TripDataBean getTripData() {
        return tripData;
    }

    public void setTripData(TripDataBean tripData) {
        this.tripData = tripData;
    }
}
