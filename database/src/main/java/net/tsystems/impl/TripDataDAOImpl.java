package net.tsystems.impl;

import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TripDataDAOImpl extends AbstractDaoImpl<TripDataDO, Integer> implements TripDataDAO {
}
