package net.tsystems.impl;

import net.tsystems.entities.RouteDO;
import net.tsystems.entitydao.RouteDAO;
import org.springframework.stereotype.Repository;

@Repository
public class RouteDAOImpl extends AbstractDaoImpl<RouteDO, Integer> implements RouteDAO {
}
