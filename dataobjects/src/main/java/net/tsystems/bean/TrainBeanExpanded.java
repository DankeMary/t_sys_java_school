package net.tsystems.bean;

import java.io.Serializable;
import java.util.List;

public class TrainBeanExpanded  implements Serializable {
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
