package net.tsystems.service;

import net.tsystems.bean.TripBean;
import net.tsystems.beanmapper.TripBeanMapper;
import net.tsystems.beanmapper.TripBeanMapperImpl;
import net.tsystems.entities.TripDO;
import net.tsystems.entitydao.TripDAO;
import net.tsystems.entitymapper.TripEntityMapper;
import net.tsystems.entitymapper.TripEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service("tripService")
@Transactional
public class TripService {

    private static final Logger LOG = LoggerFactory.getLogger(TripService.class);

    private TripDAO tripDao;
    private TripEntityMapper entityMapper = new TripEntityMapperImpl();
    private TripBeanMapper beanMapper = new TripBeanMapperImpl();

    public void create(TripBean trip) {
        try {
            tripDao.create(tripBeanToDO(trip));
        } catch (Exception e) {
            LOG.error("Failed to create trip", e);
        }
    }

    public Integer createReturnId(TripBean trip) {
        Integer id = null;
        try {
            id = tripDao.createReturnId(tripBeanToDO(trip));
        } catch (Exception e) {
            LOG.error("Failed to create trip", e);
        }
        return id;
    }

    public void update(TripBean trip) {
        try {
            tripDao.update(tripBeanToDO(trip));
        } catch (Exception e) {
            LOG.error("Failed to update trip", e);
        }
    }

    public void delete(int id) {
        try {
            tripDao.delete(tripDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete trip by id=%s", id), e);
        }
    }

    public List<TripBean> getAll() {
        List<TripBean> tripBeans = new LinkedList<>();
        try {
            tripBeans = tripDOListToBeanList(tripDao.findAll());
        } catch (Exception e) {
            LOG.error("Failed to get all trips", e);
        }
        return tripBeans;
    }

    public TripBean getTripById(int id) {
        TripBean tripBean = null;
        try {
            tripBean = tripDOToBean(tripDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get trip by id=%s", id), e);
        }
        return tripBean;
    }

    public TripBean getTripByTrainId(int id) {
        TripBean tripBean = null;
        try {
            tripBean = tripDOToBean(tripDao.getByTrainId(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get trip by train's id=%s", id), e);
        }
        return tripBean;
    }

    public boolean existByStationId(int stationId) {
        return tripDao.existByStationId(stationId);
    }

    public TripBean tripDOToBean(TripDO trip) {
        return beanMapper.tripToBean(entityMapper.tripToSO(trip));
    }

    public TripDO tripBeanToDO(TripBean trip) {
        return entityMapper.tripToDO(beanMapper.tripToSO(trip));
    }

    public List<TripBean> tripDOListToBeanList(List<TripDO> trips) {
        return beanMapper.tripListToBeanList(entityMapper.tripListToSOList(trips));
    }

    @Autowired
    public void setTripDao(TripDAO tripDao) {
        this.tripDao = tripDao;
    }
}
