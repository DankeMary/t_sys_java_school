package net.tsystems.bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateTrainForm {
    @NotNull
    @Valid
    private TrainBean train;
    @NotNull
    @Valid
    private List<StationBeanExpanded> primitivePath;

    public TrainBean getTrain() {
        return train;
    }

    public void setTrain(TrainBean train) {
        this.train = train;
    }

    public List<StationBeanExpanded> getPrimitivePath() {
        return primitivePath;
    }

    public void setPrimitivePath(List<StationBeanExpanded> primitivePath) {
        this.primitivePath = primitivePath;
    }
}
