package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TripDataDO;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TripDataDAO extends AbstractDao<TripDataDO, Integer> {
    List<TripDataDO> findFirstByTrain(int id);
    List<TripDataDO> findFirstAfterNowByTrain(int id);
    List<TripDataDO> findByTrainIdAndTripDepartureDay (int trainId, Date date);
    List<TripDataDO> getScheduleForStation(String stationName, int maxResults);
    List<TripDataDO> getDataForSection (Date fromDay,
                                        Time fromTime,
                                        Date toDay,
                                        Time toTime,
                                        String fromStation,
                                        String toStation);
}
