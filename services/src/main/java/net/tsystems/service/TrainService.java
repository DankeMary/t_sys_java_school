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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("trainService")
@Transactional
public class TrainService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);

    private TrainDAO trainDao;
    private TrainEntityMapper entityMapper = new TrainEntityMapperImpl();
    private TrainBeanMapper beanMapper = new TrainBeanMapperImpl();

    private RouteService routeService;
    private TripService tripService;
    private TripDataService tripDataService;
    private TicketService ticketService;

    public Integer create(TrainBean train) {
        Integer id = null;
        try {
            id = trainDao.createReturnObject(trainBeanToDO(train)).getId();
        } catch (Exception e) {
            LOG.error("Failed to create train");
            e.printStackTrace();
        }
        return id;
    }

    public TrainBean createReturnObject(TrainBean train) {
        TrainBean trainBean = null;
        try {
            trainBean = trainDOToBean(trainDao.createReturnObject(trainBeanToDO(train)));
        } catch (Exception e) {
            LOG.error("Failed to create train");
            e.printStackTrace();
        }
        return trainBean;
    }

    public void create(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        try {
            train = createReturnObject(train);
            routeService.createTrainPath(train, stationsData);
        } catch (Exception e) {
            LOG.error("Failed to create train with stations data passed");
            e.printStackTrace();
        }
    }

    public void update(TrainBean train) {
        try {
            //TODO !!!check if new capacity is bigger than previous one and that tickets are already sole
            trainDao.update(trainBeanToDO(train));
        } catch (Exception e) {
            LOG.error("Failed to update train");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            //TODO !!! check that no tickets were sold!!!!!
            //tripDataService.getFirstJourneysByTrainNotCancelled(id)
            tripDataService.trainWasErased(id);
            trainDao.delete(trainDao.find(id));
            //TODO cancel journeys, erase routes ?
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete train by id=%s", id));
            e.printStackTrace();
        }
    }

    public List<TrainBean> getAll() {
        List<TrainBean> trains = new LinkedList<>();
        try {
            trains = trainDOListToBeanList(trainDao.findAll());
            for (TrainBean train : trains)
                train.setTrip(tripService.getTripByTrainId(train.getId()));
        } catch (Exception e) {
            LOG.error("Failed to get all trains");
            e.printStackTrace();
        }
        return trains;
    }

    public List<TrainBean> getAll(int page, int maxResult) {
        List<TrainBean> trains = new LinkedList<>();
        try {
            trains = trainDOListToBeanList(trainDao.getAllOrdered(page, maxResult));
            for (TrainBean train : trains)
                train.setTrip(tripService.getTripByTrainId(train.getId()));
        } catch (Exception e) {
            LOG.error("Failed to get all trains");
            e.printStackTrace();
        }
        return trains;
    }

    public TrainBean getTrainById(int id) {
        TrainBean train = null;
        try {
            train = trainDOToBean(trainDao.find(id));
            if (train != null)
                train.setTrip(tripService.getTripByTrainId(train.getId()));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get train by id=%s", id));
            e.printStackTrace();
        }
        return train;
    }

    public TrainBean getTrainByNumber(int number) {
        TrainBean train = null;
        try {
            train = trainDOToBean(trainDao.findByNumber(number));
            if (train != null)
                train.setTrip(tripService.getTripByTrainId(train.getId()));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get train by number=%s", number));
            e.printStackTrace();
        }
        return train;
    }

    public TrainBeanExpanded getTrainWithPath(int id) {
        TrainBeanExpanded trainBeanExpanded;
        try {
            trainBeanExpanded = new TrainBeanExpanded();
            trainBeanExpanded.setTrainBean(getTrainById(id));
            trainBeanExpanded.getTrainBean().setTrip(tripService.getTripByTrainId(id));
            trainBeanExpanded.setTrainRoute(routeService.getTrainPathByTrainId(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get train with path by train's id=%s", id));
            e.printStackTrace();
            trainBeanExpanded = null;
        }
        return trainBeanExpanded;
    }

    //Validation Utils
    public boolean canUpdate(int trainId) {
        return !ticketService.hasTicketsOnTrainSold(trainId);
    }

    public void validate(TrainBean train, boolean isNew, Map<String, String> errors) {
        TrainBean oldTrain = isNew ? null : trainDOToBean(trainDao.find(train.getId()));
        if (!isNew && ticketService.hasTicketsOnTrainSold(train.getId())) {
            errors.put("ticketsSold", "Can't update - tickets have already been sold");
        } else {
            if (train.getNumber() != null &&
                    train.getNumber() < Integer.MAX_VALUE &&
                    train.getNumber() > 0) {
                if ((isNew && getTrainByNumber(train.getNumber().intValue()) != null) ||
                        (!isNew && !isUniqueByNumber(train.getId(), train.getNumber().intValue())))
                    errors.put("numberNonUnique", "Train with such number already exists");
            }
        }
    }

    public void validateDeletion(int trainId, Map<String, String> errors) {
        tripDataService.validateCancellation(trainId,
                tripDataService.getFirstJourneysByTrainNotCancelled(trainId, true),
                errors);
    }

    public boolean isUniqueByNumber(int id, int number) {
        return trainDao.isUniqueByNumber(id, number);
    }

    //Help Functions
    public int countPages(int maxResult) {
        return trainDao.countPages(maxResult);
    }

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

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}


