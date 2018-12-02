package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TrainDO;

import java.util.List;

public interface TrainDAO extends AbstractDao<TrainDO, Integer> {
    List<TrainDO> getAllOrdered(int page, int maxResult);
    TrainDO findByNumber(int number);
    TrainDO createReturnObject(TrainDO t);
    boolean isUniqueByNumber(int id, int number);
}
