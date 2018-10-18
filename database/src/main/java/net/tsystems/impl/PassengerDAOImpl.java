package net.tsystems.impl;

import net.tsystems.entities.PassengerDO;
import net.tsystems.entitydao.PassengerDAO;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerDAOImpl extends AbstractDaoImpl<PassengerDO, Integer> implements PassengerDAO {

}
