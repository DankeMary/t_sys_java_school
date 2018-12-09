package net.tsystems.impl;

import net.tsystems.entities.RouteDO;
import net.tsystems.entitydao.RouteDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteDAOImpl extends AbstractDaoImpl<RouteDO, Integer> implements RouteDAO {
    @Override
    public List<RouteDO> getRoutesByTrainId(int trainId) {
        List<RouteDO> list = (List<RouteDO>) getEntityManager()
                .createQuery("from RouteDO where trip.train.id = :trainId")
                .setParameter("trainId", trainId)
                .list();
        return list;
    }

    /*@Override
    public RouteDO getLastRouteByTrainId(int trainId) {
        List<RouteDO> list = (List<RouteDO>) getEntityManager()
                .createQuery("from RouteDO r where trip.train.id=" + trainId + " and r.nextStation=null")
                .list();
        return list.size() == 0 ? null : list.get(0);
    }*/

    /*@Override
    public List<RouteDO> getRoutesByTripId(int tripId) {
        List<RouteDO> list = (List<RouteDO>) getEntityManager()
                .createQuery("from RouteDO where trip.train.=" + number)
                .list();
        return list.size() == 0 ? null : list.get(0);
    }*/
}
