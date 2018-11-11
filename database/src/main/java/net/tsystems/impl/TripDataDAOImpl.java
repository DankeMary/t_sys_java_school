package net.tsystems.impl;

import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class TripDataDAOImpl extends AbstractDaoImpl<TripDataDO, Integer> implements TripDataDAO {
    public List<TripDataDO> findFirstByTrain(int id) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("from TripDataDO where route.trip.train=" + id
                        + " and route.station = route.trip.from")
                .list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public List<TripDataDO> findFirstAfterNowByTrain(int id) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("from TripDataDO where route.trip.train=" + id
                        + " and route.station = route.trip.from"
                        + " and date >= now()")
                .list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public List<TripDataDO> findByTrainIdAndTripDepartureDay(int trainId, Date date) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery("from TripDataDO td where td.route.trip.train.id=" + trainId
                        + " and tripDeparture=\'" + date + "\'")
                .list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public List<TripDataDO> getScheduleForStation(String stationName, int maxResults) {
        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
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
                .list();

        return list.size() == 0 ? null : list;
    }

    //TODO time? (type)
    @Override
    //@SuppressWarnings("unchecked")
    public List<TripDataDO> getDataForSection(Date fromDay,
                                              Time fromTime,
                                              Date toDay,
                                              Time toTime,
                                              String fromStation,
                                              String toStation) {
        //TODO check that dates and times are after right now?

        List<TripDataDO> list = (List<TripDataDO>) getEntityManager()
                .createQuery(
                        "select td1 from TripDataDO td1 " +
                        "where exists (" +
                        "select td from TripDataDO td " +
                        " join RouteDO r on td.route = r  " +
                        "join TripDO t on r.trip = t  " +
                        "join TrainDO tr on t.train = tr  " +
                        "where td.id = td1.id and " +
                        "r.station.name = \'" + fromStation + "\' and  " +
                        "td.date between \'" + fromDay + "\' and \'" + toDay + "\' and " +
                        //td.date == today == fromDay == toDay
                        "((td.date = \'" + fromDay + "\' and \'" + fromDay + "\' = \'" + toDay + "\' and " +
                        "\'" + fromDay + "\' = current_date() and " +
                        "(time(r.departure) between \'" + fromTime + "\' and \'" + toTime + "\'" +
                        " or time(r.departure) between time(now()) and \'" + toTime + "\')) " +
                        "or " +
                        //TODO Remove repetitive part?
                        //td.date == today == fromDay
                        "(\'" + fromDay + "\' = current_date() and td.date = \'" + fromDay + "\' and " +
                        "time(r.departure) >= time(now()) and time(r.departure) >= \'" + fromTime + "\') " +
                        " or " +
                        //td.date == fromDay
                        "(td.date = \'" + fromDay + "\' and time(r.departure) >= \'" + fromTime + "\')" +
                        " or " +
                        //td.date in (fromDay, toDay)
                        "(td.date > " + fromDay + " and td.date < " + toDay + ") " +
                        " or " +
                        //td.date == toDay
                        "(td.date = " + toDay + " and time(r.departure) < \'" + toTime + "\')))" +
                        " and exists (select td2 from TripDataDO td2  " +
                        "join RouteDO r2 on td2.route = r2  " +
                        "join TripDO t2 on r2.trip = t2  " +
                        "join TrainDO tr2 on t2.train = tr2  " +
                        "where td1.tripDeparture = td2.tripDeparture and r2.station.name = \'" + toStation + "\' )"
                )
                .list();
        return list;
    }
}
