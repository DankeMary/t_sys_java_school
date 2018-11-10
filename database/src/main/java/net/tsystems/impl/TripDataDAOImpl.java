package net.tsystems.impl;

import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class TripDataDAOImpl extends AbstractDaoImpl<TripDataDO, Integer> implements TripDataDAO {
    public List<TripDataDO> findFirstByTrain(int id) {
        List<TripDataDO> list = (List<TripDataDO>)getEntityManager()
                .createQuery("from TripDataDO where route.trip.train=" + id
                                        + " and route.station = route.trip.from")
                .list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public List<TripDataDO> findFirstAfterNowByTrain(int id) {
        List<TripDataDO> list = (List<TripDataDO>)getEntityManager()
                .createQuery("from TripDataDO where route.trip.train=" + id
                        + " and route.station = route.trip.from"
                        + " and date >= now()")
                .list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public List<TripDataDO> findByTrainIdAndTripDepartureDay(int trainId, Date date) {
        List<TripDataDO> list = (List<TripDataDO>)getEntityManager()
                .createQuery("from TripDataDO td where td.route.trip.train.id=" + trainId
                        + " and date=" + date)
                .list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public List<TripDataDO> getScheduleForStation(String stationName, int maxResults) {
        List<TripDataDO> list = (List<TripDataDO>)getEntityManager()
                .createQuery("select td from TripDataDO td " +
                        "join RouteDO r on td.route = r " +
                        "join TripDO t on r.trip = t " +
                        "join TrainDO tr on t.train = tr " +
                        "where r.station.name =\'" + stationName +"\' and " +
                        " td.date >= now() " +
                        " order by td.date, r.arrival")
                .setMaxResults(maxResults)
                .list();

        return list.size() == 0 ? null : list;
    }
}
