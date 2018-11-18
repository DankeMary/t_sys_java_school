package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TripDataDO;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TripDataDAO extends AbstractDao<TripDataDO, Integer> {
    List<TripDataDO> findFirstByTrain(int id);
    List<TripDataDO> findFirstAfterNowByTrain(int id);
    List<TripDataDO> findByTrainIdAndTripDepartureDay (int trainId, LocalDate date);
    List<TripDataDO> findByTripIdAndTripDepartureDay (int tripId, LocalDate date);
    boolean journeyOfTripOnDateExists (int tripId, LocalDate date);
    List<TripDataDO> getScheduleForStation(String stationName, int maxResults);
    List<TripDataDO> getDataForSection (LocalDate fromDay,
                                        LocalTime fromTime,
                                        LocalDate toDay,
                                        LocalTime toTime,
                                        String fromStation,
                                        String toStation);
}
