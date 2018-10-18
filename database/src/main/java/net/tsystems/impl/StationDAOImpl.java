package net.tsystems.impl;

import net.tsystems.entities.StationDO;
import net.tsystems.entitydao.StationDAO;
import org.springframework.stereotype.Repository;

@Repository
public class StationDAOImpl extends AbstractDaoImpl<StationDO, Integer> implements StationDAO {
}
