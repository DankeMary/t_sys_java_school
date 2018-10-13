package net.tsystems.service;


import net.tsystems.impl.PassengerDAOImpl;
import net.tsystems.mappers.PassengerEntityMapper;
import net.tsystems.mappers.PassengerEntityMapperImpl;
import net.tsystems.serviceobject.PassengerSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("passengerService")
@Transactional
public class PassengerService {

    //@Autowired
    private PassengerDAOImpl psngrDao = new PassengerDAOImpl();
    //@Autowired
    private PassengerEntityMapper mapper = new PassengerEntityMapperImpl();

    public List<PassengerSO> getAll() {
        return mapper.passengerListToSOList(psngrDao.findAll());
    }

    public PassengerSO getPassenger(int id){
        return mapper.passengerToSO(psngrDao.find(id));
    }
}
