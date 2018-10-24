package net.tsystems.impl;

import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.TrainDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainDAOImpl extends AbstractDaoImpl<TrainDO, Integer> implements TrainDAO {
    public TrainDO findByNumber(int number) {
        List<TrainDO> list = (List<TrainDO>)getEntityManager()
                .createQuery("from TrainDO where number=" + number)
                .list();
        return list.size() == 0 ? null : list.get(0);
    }

    public boolean isUniqueByNumber(int id, int number) {
        List<TrainDO> list = (List<TrainDO>)getEntityManager()
                .createQuery("from TrainDO where number=" + number + " and id<>" + id)
                .list();
        return list.size() == 0;
    }
}
