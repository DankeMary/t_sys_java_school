package net.tsystems.bean;

import java.util.List;

public class TrainBeanExpanded {
    private TrainBean trainBean;
    private List<RouteBean> trainRoute;

    public TrainBean getTrainBean() {
        return trainBean;
    }
    public void setTrainBean(TrainBean trainBean) {
        this.trainBean = trainBean;
    }

    public List<RouteBean> getTrainRoute() {
        return trainRoute;
    }
    public void setTrainRoute(List<RouteBean> trainRoute) {
        this.trainRoute = trainRoute;
    }
}
