package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TripDO;

public interface TripDAO extends AbstractDao<TripDO, Integer> {
    Integer createReturnId(TripDO t);
}
