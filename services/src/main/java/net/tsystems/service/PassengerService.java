package net.tsystems.service;


import net.tsystems.entitydao.PassengerDAO;
import net.tsystems.entitymapper.PassengerEntityMapper;
import net.tsystems.entitymapper.PassengerEntityMapperImpl;
import net.tsystems.serviceobject.PassengerSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("passengerService")
@Transactional
public class PassengerService {
    @Autowired
    private PassengerDAO psngrDao;
    private PassengerEntityMapper mapper = new PassengerEntityMapperImpl();

    public void create(PassengerSO psngr){
        psngrDao.create(mapper.passengerToDO(psngr));
    }
    public void update(PassengerSO psngr){
        psngrDao.update(mapper.passengerToDO(psngr));
    }
    public void delete(int id){
        psngrDao.delete(psngrDao.find(id));
    }
    public List<PassengerSO> getAll() {
        return mapper.passengerListToSOList(psngrDao.findAll());
    }
    public PassengerSO getPassenger(int id){
        return mapper.passengerToSO(psngrDao.find(id));
    }

}

