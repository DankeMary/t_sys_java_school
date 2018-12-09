package net.tsystems.entitydao;


import net.tsystems.AbstractDao;
import net.tsystems.entities.PassengerDO;


public interface PassengerDAO extends AbstractDao<PassengerDO, Integer> {
    PassengerDO createReturnObject(PassengerDO p);
}
