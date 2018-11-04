package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.entities.RouteDO;
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
    private TripService tripService;
    @Autowired
    private RouteDAO routeDao;
    private RouteEntityMapper routeEntityMapper = new RouteEntityMapperImpl();
    private RouteBeanMapper routeBeanMapper = new RouteBeanMapperImpl();
    @Autowired
    private StationDAO stationDao;
    private StationEntityMapper stationEntityMapper = new StationEntityMapperImpl();
    private StationBeanMapper stationBeanMapper = new StationBeanMapperImpl();
    @Autowired
    private StationService stationService;

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
    public List<RouteBean> getRoutesByTrainId(int trainId) { return routeDOListToBeanList(routeDao.getRoutesByTrainId(trainId)); }

    public void createTrainRoutes(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        TripBean trip = new TripBean();
        trip.setTrain(train);
        trip.setFrom(stationService.getStationByName(stationsData.get(1).getStation().getName()));
        trip.setTo(stationService.getStationByName(stationsData.get(stationsData.size()).getStation().getName()));
        Integer savedTripId = tripService.createReturnId(trip);

        TripBean savedTrip = tripService.getTripById(savedTripId);
        StationBean nextStation = null;
        for (int i = stationsData.size(); i > 0; i--) {
            RouteBean route = new RouteBean();
            StationBeanExpanded currRoutePoint = stationsData.get(i);
            StationBean currStation = stationService.getStationByName(currRoutePoint.getStation().getName());
            route.setStation(currStation);
            route.setNextStation(nextStation);
            route.setArrival(currRoutePoint.getArrTime());
            route.setDeparture(currRoutePoint.getDepTime());
            route.setTrip(savedTrip);
            routeDao.create(routeBeanToDO(route));
            nextStation = currStation;
        }
    }

    public List<RouteBean> getTrainPathByTrainId(int trainId) {
        List<RouteBean> trainRoutes = getRoutesByTrainId(trainId);

        Map<Integer, RouteBean> stationsData = new HashMap<Integer, RouteBean>();
        RouteBean lastRoute = null;
        //TODO: DAO get lastRoute (==null) & getPath(!=null) ?
        //Key - next station's ID
        for (RouteBean rb : trainRoutes) {
            if (rb.getNextStation() == null)
                lastRoute = rb;
            else
                stationsData.put(rb.getNextStation().getId(), rb);
        }

        //Make the ordered path
        int nextStationId = lastRoute.getStation().getId();
        RouteBean currRoute;
        List<RouteBean> orderedTrainRoutes = new LinkedList<RouteBean>();
        orderedTrainRoutes.add(lastRoute);

        for (int i = 0; i < trainRoutes.size() - 1; i++) {
            currRoute = stationsData.get(nextStationId);
            orderedTrainRoutes.add(0, currRoute);
            nextStationId = currRoute.getStation().getId();
        }

        return orderedTrainRoutes;
    }

    //Mappers
    private TrainBean trainDOToBean(TrainDO trainDO) {
        return trainBeanMapper.trainToBean(trainEntityMapper.trainToSO(trainDO));
    }

    private TrainDO trainBeanToDO(TrainBean trainBean) {
        return trainEntityMapper.trainToDO(trainBeanMapper.trainToSO(trainBean));
    }

    public RouteDO routeBeanToDO (RouteBean route) {
        return routeEntityMapper.routeToDO(routeBeanMapper.routeToSO(route));
    }

    public List<RouteBean> routeDOListToBeanList (List<RouteDO> routes) {
        return routeBeanMapper.routeListToBeanList(routeEntityMapper.routeListToSOList(routes));
    }
}
