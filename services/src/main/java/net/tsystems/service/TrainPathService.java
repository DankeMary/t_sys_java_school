package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitydao.StationDAO;
import net.tsystems.entitydao.TrainDAO;
import net.tsystems.entitydao.TripDAO;
import net.tsystems.entitymapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("trainPathService")
@Transactional
public class TrainPathService {
    /*@Autowired
    private TrainDAO trainDao;
    private TrainEntityMapper trainEntityMapper = new TrainEntityMapperImpl();
    private TrainBeanMapper trainBeanMapper = new TrainBeanMapperImpl();
    @Autowired
    private TripDAO tripDao;
    private TripEntityMapper tripEntityMapper = new TripEntityMapperImpl();
    private TripBeanMapper tripBeanMapper = new TripBeanMapperImpl();
    @Autowired
    private RouteDAO routeDao;
    private RouteEntityMapper routeEntityMapper = new RouteEntityMapperImpl();
    private RouteBeanMapper routeBeanMapper = new RouteBeanMapperImpl();
    @Autowired
    private StationDAO stationDao;
    private StationEntityMapper stationEntityMapper = new StationEntityMapperImpl();
    private StationBeanMapper stationBeanMapper = new StationBeanMapperImpl();

    public void create(Integer trainNumber, Map<Integer, StationBeanExpanded> stationsData) {
        TripBean trip = new TripBean();
        trip.setTrain(makeTrainBean(trainDao.findByNumber(trainNumber)));
        trip.setFrom(stationBeanMapper.stationToBean(stationEntityMapper.stationToSO(stationDao.findByName(stationsData.get(1).getStationName()))));
        trip.setTo(stationBeanMapper.stationToBean(stationEntityMapper.stationToSO(stationDao.findByName(stationsData.get(stationsData.size()).getStationName()))));
        Integer savedTripId = tripDao.createReturnId(tripEntityMapper.tripToDO(tripBeanMapper.tripToSO(trip)));

        TripBean savedTrip = tripBeanMapper.tripToBean(tripEntityMapper.tripToSO(tripDao.find(savedTripId)));
        StationBean nextStation = null;
        for (int i = stationsData.size(); i > 0; i--) {
            RouteBean route = new RouteBean();
            StationBeanExpanded curr = stationsData.get(i);
            StationBean currStation = stationBeanMapper.stationToBean(stationEntityMapper.stationToSO(stationDao.findByName(stationsData.get(i).getStationName())));
            route.setStation(currStation);
            route.setNextStation(nextStation);
            route.setArrival(curr.getArrTime());
            route.setDeparture(curr.getDepTime());
            route.setTrip(savedTrip);
            routeDao.create(routeEntityMapper.routeToDO(routeBeanMapper.routeToSO(route)));
            nextStation = currStation;
        }
    }

    public void getTrainPathByTrainNumber(int trainNumber){
        TrainBean train = makeTrainBean(trainDao.findByNumber(trainNumber));

    }

    private TrainBean makeTrainBean(TrainDO trainDO){
        return trainBeanMapper.trainToBean(trainEntityMapper.trainToSO(trainDO));
    }
    private TrainDO makeTrainDO(TrainBean trainBean){
        return trainEntityMapper.trainToDO(trainBeanMapper.trainToSO(trainBean));
    }*/
}
