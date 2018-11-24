package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.RouteDO;

import java.util.List;

public interface RouteDAO extends AbstractDao<RouteDO, Integer> {
    List<RouteDO> getRoutesByTrainId(int trainId);
}
