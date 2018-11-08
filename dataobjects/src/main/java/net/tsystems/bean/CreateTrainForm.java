package net.tsystems.bean;

import java.util.List;

public class CreateTrainForm {
    private TrainBean train;
    private List<PrimitiveRouteBean> primitivePath;

    public TrainBean getTrain() {
        return train;
    }

    public void setTrain(TrainBean train) {
        this.train = train;
    }

    public List<PrimitiveRouteBean> getPrimitivePath() {
        return primitivePath;
    }

    public void setPrimitivePath(List<PrimitiveRouteBean> primitivePath) {
        this.primitivePath = primitivePath;
    }
}
