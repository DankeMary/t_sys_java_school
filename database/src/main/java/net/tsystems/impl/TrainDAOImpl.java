package net.tsystems.impl;

import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.TrainDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TrainDAOImpl extends AbstractDaoImpl<TrainDO, Integer> implements TrainDAO {
}
