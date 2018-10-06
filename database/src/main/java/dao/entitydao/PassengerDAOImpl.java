package dao.entitydao;

import dao.AbstractDaoImpl;
import dataobject.PassengerDO;

import java.util.List;

public class PassengerDAOImpl extends AbstractDaoImpl<PassengerDO, Integer> implements PassengerDAO  {

    public PassengerDAOImpl() {
        super(PassengerDO.class);
    }

    @SuppressWarnings("unchecked")
    public List<PassengerDO> getAllPassengers() {
        String queryString = "SELECT t FROM " + PassengerDO.class /*super.clazz*/ +" t";
        return (List<PassengerDO>)getEntityManager().createQuery(queryString).getResultList(); //createQuery("from PassengerDO").list()
    }

    public PassengerDO getPassenger(Integer id) {
        return super.find(id);
    }

    public void updatePassenger(PassengerDO passenger) {
        super.update(passenger);
    }

    public void deletePassenger(PassengerDO passenger) {
        super.delete(passenger);
    }
}
