package net.tsystems.service;

import net.tsystems.bean.StationBean;
import net.tsystems.bean.StationBeanExpanded;
import net.tsystems.beanmapper.StationBeanMapper;
import net.tsystems.beanmapper.StationBeanMapperImpl;
import net.tsystems.entities.StationDO;
import net.tsystems.entitydao.StationDAO;
import net.tsystems.entitymapper.StationEntityMapper;
import net.tsystems.entitymapper.StationEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.LinkedList;
import java.util.List;

@Service("stationService")
@Transactional
public class StationService {

    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);

    private StationDAO stationDao;
    private StationEntityMapper entityMapper = new StationEntityMapperImpl();
    private StationBeanMapper beanMapper = new StationBeanMapperImpl();

    private TripService tripService;

    public void create(StationBean station) {
        try {
            stationDao.create(stationBeanToDO(station));
        } catch (Exception e) {
            LOG.error("Failed to create station");
            e.printStackTrace();
        }
    }

    public void update(StationBean station) {
        try {
            stationDao.update(stationBeanToDO(station));
        } catch (Exception e) {
            LOG.error("Failed to update station");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            stationDao.delete(stationDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete station by id=%s", id));
            e.printStackTrace();
        }
    }

    public List<StationBean> getAll() {
        List<StationBean> stationBeans = new LinkedList<>();
        try {
            stationBeans = stationDOListToBeanList(stationDao.findAll());
        } catch (Exception e) {
            LOG.error("Failed to get all stations");
            e.printStackTrace();
        }
        return stationBeans;
    }

    public List<StationBean> getAll(int page, int maxResult) {
        List<StationBean> stationBeans = new LinkedList<>();
        try {
            stationBeans = stationDOListToBeanList(stationDao.getAllOrdered(page, maxResult));
        } catch (Exception e) {
            LOG.error("Failed to get all stations");
            e.printStackTrace();
        }
        return stationBeans;
    }

    public StationBean getStationById(int id) {
        StationBean stationBean = null;
        try {
            stationBean = stationDOToBean(stationDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to find station by id=%s", id));
            e.printStackTrace();
        }
        return stationBean;
    }

    public StationBean getStationByName(String name) {
        StationBean stationBean = null;
        try {
            stationBean = stationDOToBean(stationDao.findByName(name));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get station by name=%s", name));
            e.printStackTrace();
        }
        return stationBean;
    }

    public boolean isUniqueByName(int id, String name) {
        return stationDao.isUniqueByName(id, name);
    }

    //Validation Utils
    public void validate(StationBean station, boolean isNew, Errors errors) {
        if ((isNew && getStationByName(station.getName()) != null) ||
                (!isNew && !isUniqueByName(station.getId(), station.getName())))
            errors.rejectValue("name", "NonUnique", "Station with such name already exists");
    }

    public boolean canDelete(int id) {
        return !tripService.existByStationId(id);
    }

    public boolean allStationsExist(List<StationBeanExpanded> trainPath) {
        List<String> stationNames = new LinkedList<>();
        for (StationBeanExpanded p : trainPath)
            if (!p.getStationName().isEmpty())
                stationNames.add(p.getStationName());

        return stationDao.allStationsExist(stationNames);
    }

    //Help Functions
    public int countPages(int maxResult) {
        return stationDao.countPages(maxResult);
    }

    //Mappers
    public StationBean stationDOToBean(StationDO station) {
        return beanMapper.stationToBean(entityMapper.stationToSO(station));
    }

    public StationDO stationBeanToDO(StationBean station) {
        return entityMapper.stationToDO(beanMapper.stationToSO(station));
    }

    public List<StationBean> stationDOListToBeanList(List<StationDO> stations) {
        return beanMapper.stationListToBeanList(entityMapper.stationListToSOList(stations));
    }

    //Autowired
    @Autowired
    public void setStationDao(StationDAO stationDao) {
        this.stationDao = stationDao;
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }
}
