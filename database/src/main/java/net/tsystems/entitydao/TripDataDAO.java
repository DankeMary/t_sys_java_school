package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TripDataDO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TripDataDAO extends AbstractDao<TripDataDO, Integer> {
    List<TripDataDO> findFirstByTrain(int id);

    int countFirstAfterNowByTrainPages(int id, int maxResult);

    List<TripDataDO> findFirstAfterNowByTrain(int id, int page, int maxResult);

    List<TripDataDO> findByTrainIdAndTripDepartureDay(int trainId, LocalDate date);

    List<TripDataDO> findByTripIdAndTripDepartureDay(int tripId, LocalDate date);

    boolean journeyOfTripOnDateExists(int tripId, LocalDate date);

    List<TripDataDO> getScheduleForStation(String stationName, int maxResults);

    List<TripDataDO> getDataForSection(LocalDateTime from,
                                       LocalDateTime to,
                                       String fromStation,
                                       String toStation,
                                       int page,
                                       int maxResult);

    int countDataForSectionPages(LocalDateTime from, LocalDateTime to,
                                 String fromStation, String toStation,
                                 int maxResult);
}
