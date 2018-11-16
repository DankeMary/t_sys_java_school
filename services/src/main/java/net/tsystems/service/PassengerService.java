package net.tsystems.service;


import net.tsystems.bean.PassengerBean;
import net.tsystems.beanmapper.PassengerBeanMapper;
import net.tsystems.beanmapper.PassengerBeanMapperImpl;
import net.tsystems.entities.PassengerDO;
import net.tsystems.entitydao.PassengerDAO;
import net.tsystems.entitymapper.PassengerEntityMapper;
import net.tsystems.entitymapper.PassengerEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service("passengerService")
@Transactional
public class PassengerService {

    private PassengerDAO psngrDao;
    private PassengerEntityMapper entityMapper = new PassengerEntityMapperImpl();
    private PassengerBeanMapper beanMapper = new PassengerBeanMapperImpl();

    public void create(PassengerBean psngr){
        psngrDao.create(passengerBeanToDO(psngr));
    }
    public PassengerBean createReturnObject(PassengerBean psngr){
        return passengerDOToBean(psngrDao.createReturnObject(passengerBeanToDO(psngr)));
    }
    public void update(PassengerBean psngr){
        psngrDao.update(passengerBeanToDO(psngr));
    }
    public void delete(int id){
        psngrDao.delete(psngrDao.find(id));
    }
    public List<PassengerBean> getAll() {
        return passengerDOListToBeanList(psngrDao.findAll());
    }
    public PassengerBean getPassenger(int id){
        return passengerDOToBean(psngrDao.find(id));
    }

    //Validation utils
    public void validate(PassengerBean passenger, Map<String, String> errors) {
        if (passenger.getBirthday().isAfter(LocalDate.now()))
            errors.put("birthdayError", "The birthday has to be in the past");
    }

    //Mappers
    private PassengerBean passengerDOToBean(PassengerDO psngr) {
        return beanMapper.passengerToBean(entityMapper.passengerToSO(psngr));
    }

    private PassengerDO passengerBeanToDO (PassengerBean psngr) {
        return entityMapper.passengerToDO(beanMapper.passengerToSO(psngr));
    }

    private List<PassengerBean> passengerDOListToBeanList(List<PassengerDO> psngrs) {
        return beanMapper.passengerListToBeanList(entityMapper.passengerListToSOList(psngrs));
    }

    //Autowired
    @Autowired
    public void setPsngrDao(PassengerDAO psngrDao) {
        this.psngrDao = psngrDao;
    }
}

