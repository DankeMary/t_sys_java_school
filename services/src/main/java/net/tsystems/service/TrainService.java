package net.tsystems.service;

import net.tsystems.bean.StationBean;
import net.tsystems.bean.StationBeanExpanded;
import net.tsystems.bean.TrainBean;
import net.tsystems.bean.TrainBeanExpanded;
import net.tsystems.beanmapper.TrainBeanMapper;
import net.tsystems.beanmapper.TrainBeanMapperImpl;
import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.TrainDAO;
import net.tsystems.entitymapper.TrainEntityMapper;
import net.tsystems.entitymapper.TrainEntityMapperImpl;
import net.tsystems.serviceobject.TrainSO;
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

    public void create(TrainBean train){
        trainDao.create(trainBeanToDO(train));
    }
    public void create(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        create(train);
        //TODO !!!  Create train with return of just-created object?
        routeService.createTrainRoutes(trainDOToBean(trainDao.findByNumber(train.getNumber().intValue())),
                                        stationsData);
    }
    public void update(TrainBean train){
        trainDao.update(trainBeanToDO(train));
    }
    public void delete(int id){
        trainDao.delete(trainDao.find(id));
    }

    public List<TrainBean> getAll() {
        return trainDOListToBeanList(trainDao.findAll());
    }
    public TrainBean getTrainById(int id){
        return trainDOToBean(trainDao.find(id));
    }
    public TrainBean getTrainByNumber(int number){
        return trainDOToBean(trainDao.findByNumber(number));
    }
    public TrainBeanExpanded getTrainWithPath(int id) {
        TrainBeanExpanded train = new TrainBeanExpanded();
        train.setTrainBean(getTrainById(id));
        train.setTrainRoute(routeService.getTrainPathByTrainId(id));
        return train;
    }

    public boolean isValid(TrainBean train, Errors errors) {
        if (train.getNumber() != null && getTrainByNumber(train.getNumber().intValue()) != null)
            errors.rejectValue("number", "NonUnique", "Train with such number already exists");
        return !errors.hasErrors();
    }
    public boolean isUniqueByNumber(int id, int number) {
        return trainDao.isUniqueByNumber(id, number);
    }

    //Mappers
    private TrainBean trainDOToBean (TrainDO train) {
        return beanMapper.trainToBean(entityMapper.trainToSO(train));
    }
    private TrainDO trainBeanToDO (TrainBean train) {
        return entityMapper.trainToDO(beanMapper.trainToSO(train));
    }
    private List<TrainBean> trainDOListToBeanList (List<TrainDO> trains) {
        return beanMapper.trainListToBeanList(entityMapper.trainListToSOList(trains));
    }

    @Autowired
    public void setTrainDao(TrainDAO trainDao) {
        this.trainDao = trainDao;
    }
    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }
}


