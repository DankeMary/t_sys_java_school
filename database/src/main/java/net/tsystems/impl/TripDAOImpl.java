package net.tsystems.impl;

import net.tsystems.entities.TripDO;
import net.tsystems.entitydao.TripDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripDAOImpl extends AbstractDaoImpl<TripDO, Integer> implements TripDAO {

    public Integer createReturnId(TripDO t) {
        //getSession().persist(t);
        t = (TripDO)getEntityManager().merge(t);
        //getSession().flush();
        return t.getId();
    }

    @Override
    public TripDO getByTrainId(int trainId) {
        List<TripDO> list = (List<TripDO>)getEntityManager()
                .createQuery("from TripDO where train=" + trainId)
                .list();
        return list.size() == 0 ? null : list.get(0);
    }
}
