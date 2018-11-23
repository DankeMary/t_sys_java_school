package net.tsystems.service;


import net.tsystems.bean.PassengerBean;
import net.tsystems.beanmapper.PassengerBeanMapper;
import net.tsystems.beanmapper.PassengerBeanMapperImpl;
import net.tsystems.entities.PassengerDO;
import net.tsystems.entitydao.PassengerDAO;
import net.tsystems.entitymapper.PassengerEntityMapper;
import net.tsystems.entitymapper.PassengerEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("passengerService")
@Transactional
public class PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    private PassengerDAO psngrDao;
    private PassengerEntityMapper entityMapper = new PassengerEntityMapperImpl();
    private PassengerBeanMapper beanMapper = new PassengerBeanMapperImpl();

    public void create(PassengerBean psngr){
        try {
            psngrDao.create(passengerBeanToDO(psngr));
        }
        catch (Exception e) {
            e.printStackTrace();
            LOG.error("Failed to create passenger");
        }
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
    public List<PassengerBean> getAll(int page, int maxResult) {
        return passengerDOListToBeanList(psngrDao.findAll(page, maxResult));
    }
    public PassengerBean getPassenger(int id){
        return passengerDOToBean(psngrDao.find(id));
    }

    //Validation utils
    public void validate(PassengerBean passenger, Map<String, String> errors) {
        if (passenger.getBirthday() != null && passenger.getBirthday().isAfter(LocalDate.now()))
            errors.put("birthdayError", "Birthday has to be in the past");
    }

    //Check that only empty lines are invalid (no incomplete information is provided)
    public boolean passengersHaveCompleteInfo(List<PassengerBean> passengers) {
        for (PassengerBean p : passengers) {
            if ((!p.getFirstName().trim().isEmpty() &&
                    !p.getLastName().trim().isEmpty() &&
                    (p.getBirthday() != null)) || (p.getFirstName().trim().isEmpty() &&
                    p.getLastName().trim().isEmpty() &&
                    (p.getBirthday() == null)))
                continue;
            else return false;
        }
        return true;
    }

    public List<PassengerBean> filterCompleteInfo(List<PassengerBean> passengers) {
        List<PassengerBean> completeInfo = new LinkedList<>();

        for (PassengerBean p : passengers) {
            if (!p.getFirstName().trim().isEmpty() &&
                    !p.getLastName().trim().isEmpty() &&
                    (p.getBirthday() != null))
                completeInfo.add(p);
        }
        return completeInfo;
    }

    public int countCompleteInfo(List<PassengerBean> passengers) {
        int count = 0;

        for (PassengerBean p : passengers) {
            if (!p.getFirstName().trim().isEmpty() &&
                    !p.getLastName().trim().isEmpty() &&
                    (p.getBirthday() != null))
                count++;
        }
        return count;
    }

    //Help Functions
    public void validateList(List<PassengerBean> passengers, Map<String, String> errors) {
        for (PassengerBean p : passengers)
            validate(p, errors);
    }

    public List<String> possibleValidationErrors(){
        List<String> errors = new LinkedList<>();
        //first & last names
        errors.add("First and Last names are required");
        errors.add("Min length - 3, Max length - 45");
        errors.add("Only latin letters, spaces and hyphens are allowed");
        //birthday
        errors.add("Birthday is required");
        errors.add("Birthday has to be in the past");
        return errors;
    }

    public int countPages(int maxResult){
        return psngrDao.countPages(maxResult);
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

