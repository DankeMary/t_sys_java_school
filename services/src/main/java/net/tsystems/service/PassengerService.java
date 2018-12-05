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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("passengerService")
@Transactional
public class PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    private PassengerDAO psngrDao;
    private PassengerEntityMapper entityMapper = new PassengerEntityMapperImpl();
    private PassengerBeanMapper beanMapper = new PassengerBeanMapperImpl();

    public void create(PassengerBean psngr) {
        try {
            psngrDao.create(passengerBeanToDO(psngr));
        } catch (Exception e) {
            LOG.error("Failed to create passenger");
            e.printStackTrace();
        }
    }

    public PassengerBean createReturnObject(PassengerBean psngr) {
        PassengerBean passengerBean = null;
        try {
            passengerBean = passengerDOToBean(psngrDao.createReturnObject(passengerBeanToDO(psngr)));
        } catch (Exception e) {
            LOG.error("Failed to create passenger");
            e.printStackTrace();
        }
        return passengerBean;
    }

    public void update(PassengerBean psngr) {
        try {
            psngrDao.update(passengerBeanToDO(psngr));
        } catch (Exception e) {
            LOG.error("Failed to update passenger");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            psngrDao.delete(psngrDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete passenger by id=%s", id));
            e.printStackTrace();
        }
    }

    public List<PassengerBean> getAll() {
        List<PassengerBean> passengers = new LinkedList<>();
        try {
            passengers = passengerDOListToBeanList(psngrDao.findAll());
        } catch (Exception e) {
            LOG.error("Failed to get all passengers");
            e.printStackTrace();
        }
        return passengers;
    }

    public List<PassengerBean> getAll(int page, int maxResult) {
        List<PassengerBean> passengers = new LinkedList<>();
        try {
            passengers = passengerDOListToBeanList(psngrDao.findAll(page, maxResult));
        } catch (Exception e) {
            LOG.error("Failed to get all passengers");
            e.printStackTrace();
        }
        return passengers;
    }

    public PassengerBean getPassenger(int id) {
        PassengerBean passengerBean = null;
        try {
            passengerBean = passengerDOToBean(psngrDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get passenger by id=%s", id));
            e.printStackTrace();
        }
        return passengerBean;
    }

    public void validate(PassengerBean passenger, Map<String, String> errors) {
        if (passenger.getBirthday() != null && passenger.getBirthday().isAfter(LocalDate.now()))
            errors.put("birthdayError", "Birthday has to be in the past");
        else if (passenger.getBirthday() != null && LocalDate.of(1900, 1, 1).isAfter(passenger.getBirthday()))
            errors.put("birthdayError", "Minimal birthday date is 01-01-1900");
    }

    public boolean passengersHaveCompleteAndValidInfo(List<PassengerBean> passengers) {
        for (PassengerBean p : passengers) {
            if (p.getFirstName().trim().isEmpty() &&
                    p.getLastName().trim().isEmpty() &&
                    (p.getBirthday() == null))
                continue;
            else if (!p.getFirstName().trim().isEmpty() &&
                    !p.getLastName().trim().isEmpty() &&
                    (p.getBirthday() != null) && validBean(p))
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

    private boolean validBean(PassengerBean pBean) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PassengerBean>> violations = validator.validate(pBean);
        return violations.isEmpty();
    }
    public void validateList(List<PassengerBean> passengers, Map<String, String> errors) {
        for (PassengerBean p : passengers) {
            validate(p, errors);
        }
    }

    public List<String> possibleValidationErrors() {
        List<String> errors = new LinkedList<>();
        //first & last names
        errors.add("First and Last names are required");
        errors.add("Min length - 3, Max length - 45");
        errors.add("Only latin letters, spaces and hyphens are allowed");
        //birthday
        errors.add("Birthday is required");
        errors.add("Birthday has to be in the past");
        errors.add("Minimal birthday date is 01-01-1900");
        return errors;
    }

    public int countPages(int maxResult) {
        return psngrDao.countPages(maxResult);
    }

    public PassengerBean passengerDOToBean(PassengerDO psngr) {
        return beanMapper.passengerToBean(entityMapper.passengerToSO(psngr));
    }

    public PassengerDO passengerBeanToDO(PassengerBean psngr) {
        return entityMapper.passengerToDO(beanMapper.passengerToSO(psngr));
    }

    public List<PassengerBean> passengerDOListToBeanList(List<PassengerDO> psngrs) {
        return beanMapper.passengerListToBeanList(entityMapper.passengerListToSOList(psngrs));
    }

    @Autowired
    public void setPsngrDao(PassengerDAO psngrDao) {
        this.psngrDao = psngrDao;
    }
}

