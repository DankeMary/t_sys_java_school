package net.tsystems.impl;

import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.TrainDAO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainDAOImpl extends AbstractDaoImpl<TrainDO, Integer> implements TrainDAO {
    @Override
    public List<TrainDO> getAllOrdered(int page, int maxResult) {
        Query q = getEntityManager().createQuery( "from TrainDO t order by number");
        return findAll(q, page, maxResult);
    }

    public TrainDO findByNumber(int number) {
        List<TrainDO> list = (List<TrainDO>)getEntityManager()
                .createQuery("from TrainDO where number=" + number)
                .list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public TrainDO createReturnObject(TrainDO t) {
         getEntityManager().persist(t);
         return t;
    }

    public boolean isUniqueByNumber(int id, int number) {
        List<TrainDO> list = (List<TrainDO>)getEntityManager()
                .createQuery("from TrainDO where number=" + number + " and id<>" + id)
                .list();
        return list.size() == 0;
    }
}
