package net.tsystems.impl;

import net.tsystems.entities.StationDO;
import net.tsystems.entitydao.StationDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StationDAOImpl extends AbstractDaoImpl<StationDO, Integer> implements StationDAO {
    public StationDO findByName(String name) {
        List<StationDO> list = (List<StationDO>)getEntityManager()
                                    .createQuery("from StationDO where name='" + name + "'")
                                    .list();
        return list.size() == 0 ? null : list.get(0);
    }

    public boolean isUniqueByName(int id, String name) {
        List<StationDO> list = (List<StationDO>)getEntityManager()
                .createQuery("from StationDO where name='" + name + "' and id<>" + id)
                .list();
        return list.size() == 0;
    }
}
