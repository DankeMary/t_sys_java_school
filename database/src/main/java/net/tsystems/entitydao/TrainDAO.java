package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TrainDO;

public interface TrainDAO extends AbstractDao<TrainDO, Integer> {
    TrainDO findByNumber(int number);
    boolean isUniqueByNumber(int id, int number);
}
