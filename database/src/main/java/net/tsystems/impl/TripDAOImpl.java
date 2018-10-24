package net.tsystems.impl;

import net.tsystems.entities.TripDO;
import net.tsystems.entitydao.TripDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TripDAOImpl extends AbstractDaoImpl<TripDO, Integer> implements TripDAO {

    public Integer createReturnId(TripDO t) {
        //getSession().persist(t);
        t = (TripDO)getEntityManager().merge(t);
        //getSession().flush();
        return t.getId();
    }
}
