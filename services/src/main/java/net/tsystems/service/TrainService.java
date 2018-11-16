package net.tsystems.service;

import net.tsystems.bean.StationBeanExpanded;
import net.tsystems.bean.TrainBean;
import net.tsystems.bean.TrainBeanExpanded;
import net.tsystems.beanmapper.TrainBeanMapper;
import net.tsystems.beanmapper.TrainBeanMapperImpl;
import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.TrainDAO;
import net.tsystems.entitymapper.TrainEntityMapper;
import net.tsystems.entitymapper.TrainEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

@Service("trainService")
@Transactional
public class TrainService {

    private TrainDAO trainDao;
    private TrainEntityMapper entityMapper = new TrainEntityMapperImpl();
    private TrainBeanMapper beanMapper = new TrainBeanMapperImpl();

    private RouteService routeService;
    private TripService tripService;
    private TripDataService tripDataService;

    public Integer create(TrainBean train) {
        return trainDao.createReturnObject(trainBeanToDO(train)).getId();
    }

    public TrainBean createReturnObject(TrainBean train) {
        return trainDOToBean(trainDao.createReturnObject(trainBeanToDO(train)));
    }

    public void create(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        train = createReturnObject(train);

        routeService.createTrainPath(train,
                stationsData);
    }

    public void update(TrainBean train) {
        trainDao.update(trainBeanToDO(train));
    }

    public void delete(int id) {
        trainDao.delete(trainDao.find(id));
    }

    public List<TrainBean> getAll() {
        List<TrainBean> trains = trainDOListToBeanList(trainDao.findAll());
        for (TrainBean train : trains)
            train.setTrip(tripService.getTripByTrainId(train.getId()));
        return trains;
    }

    public TrainBean getTrainById(int id) {
        TrainBean train = trainDOToBean(trainDao.find(id));
        if (train != null)
            train.setTrip(tripService.getTripByTrainId(train.getId()));
        return train;
    }

    public TrainBean getTrainByNumber(int number) {
        TrainBean train = trainDOToBean(trainDao.findByNumber(number));
        if (train != null)
            train.setTrip(tripService.getTripByTrainId(train.getId()));
        return train;
    }

    public TrainBeanExpanded getTrainWithPath(int id) {
        TrainBeanExpanded train = new TrainBeanExpanded();
        train.setTrainBean(getTrainById(id));
        train.getTrainBean().setTrip(tripService.getTripByTrainId(id));
        train.setTrainRoute(routeService.getTrainPathByTrainId(id));
        return train;
    }

    //Validation Utils
    public void validate(TrainBean train, boolean isNew, Errors errors) {
        if (train.getNumber() != null &&
                train.getNumber() < Integer.MAX_VALUE &&
                train.getNumber() > 0) {
            if ((isNew && getTrainByNumber(train.getNumber().intValue()) != null) ||
                    (!isNew && !isUniqueByNumber(train.getId(), train.getNumber().intValue())))
                errors.rejectValue("number", "NonUnique", "Train with such number already exists");
        }
        //TODO!!! (check no tickets)
        if (!isNew && tripDataService.getFirstJourneysByTrainNotCancelled(train.getId(), true).size() != 0)
            //TODO in general or only if no tickets were sold yet?
            errors.rejectValue("capacity", "CannotUpdate", "There are journeys planned already");

        //TODO check that can update the price when no tickets were sold yet
    }

    public boolean isUniqueByNumber(int id, int number) {
        return trainDao.isUniqueByNumber(id, number);
    }

    //Help Functions


    //Mappers
    private TrainBean trainDOToBean(TrainDO train) {
        return beanMapper.trainToBean(entityMapper.trainToSO(train));
    }

    private TrainDO trainBeanToDO(TrainBean train) {
        return entityMapper.trainToDO(beanMapper.trainToSO(train));
    }

    private List<TrainBean> trainDOListToBeanList(List<TrainDO> trains) {
        return beanMapper.trainListToBeanList(entityMapper.trainListToSOList(trains));
    }

    //Autowired
    @Autowired
    public void setTrainDao(TrainDAO trainDao) {
        this.trainDao = trainDao;
    }

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }
}


