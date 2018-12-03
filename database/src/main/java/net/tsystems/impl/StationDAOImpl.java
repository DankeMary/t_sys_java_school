package net.tsystems.impl;

import net.tsystems.entities.StationDO;
import net.tsystems.entitydao.StationDAO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StationDAOImpl extends AbstractDaoImpl<StationDO, Integer> implements StationDAO {
    @Override
    public List<StationDO> getAllOrdered(int page, int maxResult) {
        Query q = getEntityManager().createQuery("from StationDO st order by name");
        return findAll(q, page, maxResult);
    }

    public StationDO findByName(String name) {
        List<StationDO> list = (List<StationDO>) getEntityManager()
                .createQuery("from StationDO where name = :name")
                .setParameter("name", name)
                .list();
        return list.size() == 0 ? null : list.get(0);
    }

    public boolean isUniqueByName(int id, String name) {
        List<StationDO> list = (List<StationDO>) getEntityManager()
                .createQuery("from StationDO where name = :name and id <> :id")
                .setParameter("name", name)
                .setParameter("id", id)
                .list();
        return list.size() == 0;
    }

    @Override
    public boolean allStationsExist(List<String> stationNames) {
        List<StationDO> list = (List<StationDO>) getEntityManager()
                .createQuery("from StationDO where name in (:names)")
                .setParameter("names", stationNames)
                .list();
        return list.size() == stationNames.size();
    }
}
