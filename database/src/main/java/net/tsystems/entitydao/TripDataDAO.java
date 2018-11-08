package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TripDataDO;

import java.sql.Date;
import java.util.List;

public interface TripDataDAO extends AbstractDao<TripDataDO, Integer> {
    List<TripDataDO> findFirstByTrain(int id);
    List<TripDataDO> findFirstAfterNowByTrain(int id);
    List<TripDataDO> findByTrainIdAndTripDepartureDay (int trainId, String date);
}
