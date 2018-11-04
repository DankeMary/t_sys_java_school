package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.entities.TrainDO;
import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitydao.StationDAO;
import net.tsystems.entitydao.TrainDAO;
import net.tsystems.entitydao.TripDAO;
import net.tsystems.entitymapper.*;
import net.tsystems.serviceobject.RouteSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("routeService")
@Transactional
public class RouteService {
    @Autowired
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

    public void create(RouteSO psngr) {
        routeDao.create(routeEntityMapper.routeToDO(psngr));
    }

    public void update(RouteSO psngr) {
        routeDao.update(routeEntityMapper.routeToDO(psngr));
    }

    public void delete(int id) {
        routeDao.delete(routeDao.find(id));
    }

    public List<RouteSO> getAll() {
        return routeEntityMapper.routeListToSOList(routeDao.findAll());
    }

    public RouteSO getRoute(int id) {
        return routeEntityMapper.routeToSO(routeDao.find(id));
    }

    public void createTrainRoutes(Integer trainNumber, Map<Integer, StationDataBean> stationsData) {
        TripBean trip = new TripBean();
        trip.setTrain(makeTrainBean(trainDao.findByNumber(trainNumber)));
        trip.setFrom(stationBeanMapper.stationToBean(stationEntityMapper.stationToSO(stationDao.findByName(stationsData.get(1).getStationName()))));
        trip.setTo(stationBeanMapper.stationToBean(stationEntityMapper.stationToSO(stationDao.findByName(stationsData.get(stationsData.size()).getStationName()))));
        Integer savedTripId = tripDao.createReturnId(tripEntityMapper.tripToDO(tripBeanMapper.tripToSO(trip)));

        TripBean savedTrip = tripBeanMapper.tripToBean(tripEntityMapper.tripToSO(tripDao.find(savedTripId)));
        StationBean nextStation = null;
        for (int i = stationsData.size(); i > 0; i--) {
            RouteBean route = new RouteBean();
            StationDataBean curr = stationsData.get(i);
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

    public TrainPathBean getTrainPathByTrainId(int trainId) {
        TrainBean train = makeTrainBean(trainDao.find(trainId));
        List<RouteBean> trainRoutes = routeBeanMapper.routeListToBeanList(routeEntityMapper.routeListToSOList(routeDao.getRoutesByTrainId(trainId)));

        //Create a dictionary where key is next station's id
        Map<Integer, RouteBean> stationsData = new HashMap<Integer, RouteBean>();
        RouteBean lastRoute = null;
        for (RouteBean rb : trainRoutes) {
            if (rb.getNextStation() == null)
                lastRoute = rb;
            else
                stationsData.put(rb.getNextStation().getId(), rb);
        }

        //Order path parts
        int nextStationId = lastRoute.getStation().getId();
        RouteBean currRoute;
        List<RouteBean> orderedTrainRoutes = new LinkedList<RouteBean>();
        orderedTrainRoutes.add(lastRoute);

        for (int i = 0; i < trainRoutes.size() - 1; i++) {
            currRoute = stationsData.get(nextStationId);
            orderedTrainRoutes.add(0, currRoute);
            nextStationId = currRoute.getStation().getId();
        }

        //Create an ordered list of station-timing objects
        List<StationDataBean> stationsTimingData = new LinkedList<StationDataBean>();
        for (RouteBean rb : orderedTrainRoutes){
            StationDataBean stationData = new StationDataBean();
            stationData.setRouteId(rb.getId());
            stationData.setStationName(rb.getStation().getName());
            stationData.setArrTime(rb.getArrival());
            stationData.setDepTime(rb.getDeparture());
            stationsTimingData.add(stationData);
        }

        TrainPathBean trainPath = new TrainPathBean();
        trainPath.setTrainNumber(train.getNumber().intValue());
        trainPath.setStationsData(stationsTimingData);

        return trainPath;
    }

    private TrainBean makeTrainBean(TrainDO trainDO) {
        return trainBeanMapper.trainToBean(trainEntityMapper.trainToSO(trainDO));
    }

    private TrainDO makeTrainDO(TrainBean trainBean) {
        return trainEntityMapper.trainToDO(trainBeanMapper.trainToSO(trainBean));
    }
}
