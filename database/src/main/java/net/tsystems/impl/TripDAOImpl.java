package net.tsystems.impl;

import net.tsystems.entities.TripDO;
import net.tsystems.entitydao.TripDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TripDAOImpl extends AbstractDaoImpl<TripDO, Integer> implements TripDAO {
}
