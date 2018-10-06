package dao.entitydao;

import dataobject.PassengerDO;

import java.util.List;

public interface PassengerDAO {
    public List<PassengerDO> getAllPassengers();
    public PassengerDO getPassenger(Integer id);
    public void updatePassenger(PassengerDO passenger);
    public void deletePassenger(PassengerDO passenger);
}
