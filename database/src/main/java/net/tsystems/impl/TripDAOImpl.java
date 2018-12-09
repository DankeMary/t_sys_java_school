package net.tsystems.impl;

import net.tsystems.entities.TripDO;
import net.tsystems.entitydao.TripDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripDAOImpl extends AbstractDaoImpl<TripDO, Integer> implements TripDAO {

    public Integer createReturnId(TripDO t) {
        getEntityManager().persist(t);
        return t.getId();
    }

    @Override
    public TripDO getByTrainId(int trainId) {
        List<TripDO> list = (List<TripDO>)getEntityManager()
                .createQuery("from TripDO where train.id = :trainId")
                .setParameter("trainId", trainId)
                .list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public boolean existByStationId(int stationId) {
        List<TripDO> list = (List<TripDO>)getEntityManager()
                .createQuery("from TripDO t where (t.from.id = :stationId or " +
                        "t.to.id = :stationId) and " +
                        "t.train is not null ")
                .setParameter("stationId", stationId)
                .list();
        return list.size() != 0;
    }
}
