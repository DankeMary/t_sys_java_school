package net.tsystems.impl;

import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import org.springframework.stereotype.Repository;

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
}
