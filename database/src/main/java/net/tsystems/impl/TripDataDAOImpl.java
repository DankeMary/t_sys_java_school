package net.tsystems.impl;

import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TripDataDAOImpl extends AbstractDaoImpl<TripDataDO, Integer> implements TripDataDAO {
    public List<TripDataDO> findFirstByTrain(int id) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("from TripDataDO where route.trip.train=" + id
                        + " and route.station = route.trip.from " +
                        " and isCancelled = 0 ")
                .list();
        return list;
    }

    @Override
    public int countFirstAfterNowByTrainPages(int id, int maxResult) {
        return countPages(getEntityManager()
                .createQuery( "select count(*) from TripDataDO " +
                        " where route.trip.train.id=" + id
                        + " and route.station = route.trip.from"
                        + " and date >= now() " +
                        " and isCancelled = 0"),
                maxResult);
    }

    @Override
    public List<TripDataDO> findFirstAfterNowByTrain(int id, int page, int maxResult) {
        Query q = getEntityManager()
                .createQuery("from TripDataDO " +
                        " where route.trip.train.id=" + id
                        + " and route.station = route.trip.from"
                        + " and date >= now() " +
                        " and isCancelled = 0 ");
        q.setFirstResult((page - 1) * maxResult);
        q.setMaxResults(maxResult);

        return (List<TripDataDO>) q.list();
    }

    @Override
    public List<TripDataDO> findByTrainIdAndTripDepartureDay(int trainId, LocalDate date) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("from TripDataDO td where td.route.trip.train.id=" + trainId
                        + " and tripDeparture=\'" + date + "\'" +
                        "order by td.date")
                .list();
        return list;
    }

    @Override
    public List<TripDataDO> findByTripIdAndTripDepartureDay(int tripId, LocalDate date) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("from TripDataDO td where td.route.trip.id=" + tripId
                        + " and tripDeparture=\'" + date + "\'" +
                        "order by td.date")
                .list();
        return list;
    }

    @Override
    public boolean journeyOfTripOnDateExists(int tripId, LocalDate date) {
        Long qty = (Long) getEntityManager()
                .createQuery("select count(*) as n from TripDataDO td where td.route.trip.id=" + tripId
                        + " and tripDeparture=\'" + date + "\'").uniqueResult();
        return qty != 0;
    }

    @Override
    public List<TripDataDO> getScheduleForStation(String stationName, int maxResults) {
        /*List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("select td from TripDataDO td " +
                        "join RouteDO r on td.route = r " +
                        "join TripDO t on r.trip = t " +
                        "join TrainDO tr on t.train = tr " +
                        "where r.station.name =\'" + stationName + "\' and " +
                        " (td.date > current_date() or" +
                        " (td.date = current_date() and " +
                        "((hour(r.arrival) >= hour(now()) and" +
                        " minute(r.arrival) >= minute(now())) or " +
                        "((hour(r.departure) >= hour(now()) and " +
                        " minute(r.departure) >= minute(now()))))))" +
                        " order by td.date, r.arrival")
                .setMaxResults(maxResults)
                .list();*/
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("select td from TripDataDO td " +
                        "join RouteDO r on td.route = r " +
                        "join TripDO t on r.trip = t " +
                        "join TrainDO tr on t.train = tr " +
                        "where r.station.name =\'" + stationName + "\' and " +
                        " td.date >= now() " +
                        " order by td.date, r.arrival")
                .setMaxResults(maxResults)
                .list();

        return list;
    }

    @Override
    public List<TripDataDO> getDataForSection(LocalDateTime from, LocalDateTime to,
                                              String fromStation, String toStation,
                                              int page, int maxResult) {

        LocalDateTime tenMinutesMore = LocalDateTime.now().plusMinutes(10);
        Query q = getEntityManager()
                .createQuery(
                        "select td1 from TripDataDO td1 " +
                                "where exists (" +
                                "select td from TripDataDO td " +
                                " join RouteDO r on td.route = r  " +
                                "join TripDO t on r.trip = t  " +
                                "join TrainDO tr on t.train = tr " +
                                "where td.id = td1.id and " +
                                "r.station.name = \'" + fromStation + "\' and  " +
                                "td.date between \'" + from + "\' and \'" + to + "\' and " +
                                "td.date between \'" + tenMinutesMore + "\' and \'" + to + "\')" +

                                " and exists (select td2 from TripDataDO td2  " +
                                "join RouteDO r2 on td2.route = r2  " +
                                "join TripDO t2 on r2.trip = t2  " +
                                "join TrainDO tr2 on t2.train = tr2  " +
                                "where td1.route.trip = td2.route.trip and " +
                                "td1.tripDeparture = td2.tripDeparture and " +
                                "r2.station.name = \'" + toStation + "\' )"
                );

        q.setFirstResult((page - 1) * maxResult);
        q.setMaxResults(maxResult);

        return (List<TripDataDO>) q.list();
    }

    @Override
    public int countDataForSectionPages(LocalDateTime from, LocalDateTime to,
                                        String fromStation, String toStation,
                                        int maxResult) {
        LocalDateTime tenMinutesMore = LocalDateTime.now().plusMinutes(10);
        return countPages(getEntityManager()
                        .createQuery(
                                "select count(*) from TripDataDO td1 " +
                                        "where exists (" +
                                        "select td from TripDataDO td " +
                                        " join RouteDO r on td.route = r  " +
                                        "join TripDO t on r.trip = t  " +
                                        "join TrainDO tr on t.train = tr " +
                                        "where td.id = td1.id and " +
                                        "r.station.name = \'" + fromStation + "\' and  " +
                                        "td.date between \'" + from + "\' and \'" + to + "\' and " +
                                        "td.date between \'" + tenMinutesMore + "\' and \'" + to + "\')" +

                                        " and exists (select td2 from TripDataDO td2  " +
                                        "join RouteDO r2 on td2.route = r2  " +
                                        "join TripDO t2 on r2.trip = t2  " +
                                        "join TrainDO tr2 on t2.train = tr2  " +
                                        "where td1.route.trip = td2.route.trip and " +
                                        "td1.tripDeparture = td2.tripDeparture and " +
                                        "r2.station.name = \'" + toStation + "\' )"
                        ),
                maxResult
        );
    }
}
